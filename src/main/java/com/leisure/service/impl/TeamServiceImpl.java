package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Client;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.TeamResource;
import com.leisure.entity.mapping.TeamMapper;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.TeamRepository;
import com.leisure.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TeamMapper mapper;
    private final String ENTITY = "Team";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Team save(Team team) throws Exception {
        Optional<Client> parentUser = this.clientRepository.findById(team.getParentId());
        Optional<Client> childUser = this.clientRepository.findById(team.getChildId());
        if(parentUser.isEmpty()){
            throw new ResourceNotFoundException("Cliente", team.getParentId());
        } else if(childUser.isEmpty()){
            throw new ResourceNotFoundException("Cliente", team.getChildId());
        }

        Boolean childUserInTeam = this.teamRepository.existsByChildId(team.getChildId());
        if(Boolean.TRUE.equals(childUserInTeam)){
            throw new RuntimeException(String.format("El usuario %s %s ya se encuentra en un equipo.", childUser.get().getName(), childUser.get().getLastName()));
        }

        return this.teamRepository.save(team);
    }

    @Override
    public Team update(Team team, Long teamId) throws Exception {
        Optional<Team> optionalTeam = this.teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, teamId);
        }
        Team teamUpdate = optionalTeam.get();
        teamUpdate.setIsActive(team.getIsActive());
        return this.teamRepository.save(teamUpdate);
    }

    @Override
    public Team getTeamById(Long teamId) throws Exception {
        Optional<Team> optionalTeam = this.teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, teamId);
        }
        return optionalTeam.get();
    }

    @Override
    public List<Team> getAllTeams() throws Exception {
        List<Team> teamList = this.teamRepository.findAll();
        if(teamList.isEmpty()){
            throw new RuntimeException("No hay ningun equipo creado");
        }
        return teamList;
    }

    @Override
    public Map<Object, List<TeamResource>> getTeams() throws Exception {
        List<Team> teamList = this.teamRepository.findAll();
        if(teamList.isEmpty()){
            throw new RuntimeException("No hay ningun equipo creado");
        }
        List<TeamResource> teamResource = mapper.modelListToList(teamList);
        return teamResource.stream()
                .collect(Collectors.groupingBy(team -> team.getParentId()));

    }

    @Override
    public Map<Object, List<TeamResource>> getAllTeamsGrouped(Long parentId) throws Exception {
        Optional<Client> parentUser = this.clientRepository.findById(parentId);
        if(parentUser.isEmpty()){
            throw new ResourceNotFoundException("Cliente", parentId);
        }
        Boolean parentUserInTeam = this.teamRepository.existsByParentId(parentId);
        if(!parentUserInTeam){
            throw new RuntimeException(String.format("El usuario %s %s no se encuentra en ningun equipo.", parentUser.get().getName(), parentUser.get().getLastName()));
        }
        List<Team> teamList = this.teamRepository.getTeamsByParentId(parentId);
        List<TeamResource> teamResource = mapper.modelListToList(teamList);

        return teamResource.stream()
                .sorted((f1, f2) -> ((Date)f1.getCreated_date()).compareTo(f2.getCreated_date()))
                .collect(Collectors.groupingBy(team -> team.getParentId()));
    }
    @Override
    public Map<Integer, List<Map<Object, List<TeamResource>>>> getTeamHierarchy(Long parentId) throws Exception {
        Boolean parentUserInTeam = this.teamRepository.existsByParentId(parentId);
        if(!parentUserInTeam){
            throw new RuntimeException(String.format("El usuario %d no es parent de ningun equipo.", parentId));
        }
        Integer maxLevel = 9;
        Integer level = 0;
        List<Long> currentParents = new ArrayList<>();
        Map<Integer, List<Map<Object, List<TeamResource>>>> clients = new HashMap<>();
        level++;
        while (level < maxLevel+1){
            List<Map<Object, List<TeamResource>>> maps = new ArrayList<>();
            if(level.equals(1)){
                maps.add(this.getAllTeamsGrouped(parentId));
                currentParents.add(parentId);
            } else {
                List<Team> teamList = this.teamRepository.getAllTeamByParentIdList(currentParents);
                currentParents.clear();
                List<Long> childsId = teamList.stream().map(Team::getChildId).collect(Collectors.toList());
                List<Team> newLevelList = this.teamRepository.getAllTeamByParentIdList(childsId);
                Map<Object, List<Team>> teamMap = this.groupResultByParentId(newLevelList);
                List<Long> parentIdWithTeam = new ArrayList<>();
                for (Long id : childsId) {
                    if (teamMap.containsKey(id)) {
                        parentIdWithTeam.add(id);
                    }
                }
                for (Long aLong : parentIdWithTeam) {
                    maps.add(this.getAllTeamsGrouped(aLong));
                    currentParents.add(aLong);
                }
            }
            clients.put(level, maps);
            level++;
        }
    return clients;
    }
    public Map<Object, List<Team>> groupResultByParentId(List<Team> teamList){
        return teamList.stream()
                .sorted((f1, f2) -> ((Date)f1.getCreated_date()).compareTo(f2.getCreated_date()))
                .collect(Collectors.groupingBy(team -> team.getParentId()));
    }
}
