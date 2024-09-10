package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Admin;
import com.leisure.entity.Client;
import com.leisure.entity.Status;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Client.ClientsCountResource;
import com.leisure.entity.enumeration.StatusName;
import com.leisure.repository.AdminRepository;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.StatusRepository;
import com.leisure.repository.TeamRepository;
import com.leisure.service.AdminService;
import com.leisure.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Admin save(Admin admin) throws Exception {
        Boolean existsByEmail = this.adminRepository.existsByEmail(admin.getEmail());
        Boolean existsByDni = this.adminRepository.existsByDni(admin.getDni());
        if(Boolean.TRUE.equals(existsByDni)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el dni %s", admin.getDni()));
        } else if(Boolean.TRUE.equals(existsByEmail)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el email %s", admin.getEmail()));
        }
        return this.authService.registerAdmin(admin);
    }

    @Override
    public Admin update(Admin admin, Long adminId) throws Exception {
        Optional<Admin> adminOptional = this.adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new ResourceNotFoundException("Admin", adminId);
        }
        Admin adminUpdate = adminOptional.get();
        adminUpdate.setAlias(admin.getAlias());
        return this.adminRepository.save(adminUpdate);
    }


    @Override
    public List<Admin> getAllAdmins() throws Exception {
        List<Admin> adminList = this.adminRepository.findAll();
        if(adminList.isEmpty()){
            throw new RuntimeException("No se encontró ningún admin");
        }
        return adminList;
    }

    @Override
    public Admin getAdminById(Long adminId) {
        Optional<Admin> adminOptional = this.adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new ResourceNotFoundException("Admin", adminId);
        }
        return adminOptional.get();
    }

    @Override
    public String changeClientStatus(Long clientId) {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("CLIENTE", clientId);
        }
        Client client = optionalClient.get();
        Optional<Team> team = this.teamRepository.getTeamByChildId(clientId);
        Team t = null;
        if(team.isPresent()) t = team.get();
        List<Status> status = this.statusRepository.findAll();
        if(StatusName.ACTIVO.equals(client.getStatus().getName())){
            client.setStatus(status.stream().filter(x -> x.getName().equals(StatusName.INACTIVO)).findFirst().get());
            if (t!=null) t.setIsActive(false);
        } else {
            if(client.getBonus() == null || client.getBonus().equals(0)) client.setBonus(3);
            client.setStatus(status.stream().filter(x -> x.getName().equals(StatusName.ACTIVO)).findFirst().get());
            if (t!=null) t.setIsActive(true);
        }
        this.clientRepository.save(client);
        if(t!=null) this.teamRepository.save(t);

        return String.format("Se cambió el estado del cliente con DNI %s a %s", client.getDni(), client.getStatus().getName());
    }

    @Override
    public Admin getAdminByDni(String dni) {
        Optional<Admin> optionalAdmin = this.adminRepository.getAdminByDni(dni);
        if(optionalAdmin.isEmpty()) throw new ResourceNotFoundException("Admin", dni);
        return optionalAdmin.get();
    }

    @Override
    public String deleteClient(Long clientId) {
        String message = "";
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("CLIENTE", clientId);
        }
        Client client = optionalClient.get();
        List<Team> team = this.teamRepository.getTeamsByParentId(clientId);

        if(team.isEmpty()) {
            Optional<Team> tChild = this.teamRepository.getTeamByChildId(clientId);
            if(tChild.isPresent()) this.teamRepository.deleteByChildId(clientId);
            this.clientRepository.deleteById(clientId);
            message = String.format("Cliente con dni %s ha sido eliminado.", client.getDni());
        } else {
            Long parentId = client.getId();

            // List<Long> parentsIdList = clientsInactive.stream().map(x -> x.getId()).collect(Collectors.toList());
            // List<Team> teamList = this.teamRepository.getAllTeamByParentIdList(parentsIdList);
            Map<Object, List<Team>> teamMap = this.groupResultByParentId(team);

            List<Long> parentIdWithTeam = new ArrayList<>();
            parentIdWithTeam.add(parentId);
/*            for (Long parentId : parentsIdList) {
                if (teamMap.containsKey(parentId)) {
                    parentIdWithTeam.add(parentId);
                }
            }*/

            List<Team> oldMembers = teamMap.values().stream()
                    .flatMap(List::stream)
                    .filter(Team::getIsActive)
                    .collect(Collectors.groupingBy(
                            teamMapOld -> teamMapOld.getParentId(),
                            Collectors.minBy(Comparator.comparing(teamMapOld -> teamMapOld.getCreated_date()))
                    ))
                    .values().stream()
                    .map(Optional::get)
                    .collect(Collectors.toList());
            List<Long> newParents = oldMembers.stream().map(x -> {
                x.setIsActive(false);
                this.teamRepository.save(x);
                return x.getChildId();
            }).collect(Collectors.toList());
            Map<Long, Map<String, Long>> resultMap = new HashMap<>();

            if(newParents.isEmpty()){
                Optional<Team> teamChild = this.teamRepository.getTeamByChildId(clientId);
                Team tChild = teamChild.get();
                Map<String, Long> childMap = new HashMap<>();
                childMap.put("parentId", tChild.getParentId());
                childMap.put("childId", team.get(0).getChildId());
                Long newParentId = null;
                for (int i = 0; i < team.size(); i++) {
                    Optional<Team> teamUpdate;
                    if(i == 0){
                        teamUpdate = this.teamRepository.getTeamByChildId(team.get(0).getChildId());
                        Team teamU = teamUpdate.get();
                        teamU.setParentId(tChild.getParentId());
                        this.teamRepository.save(teamU);
                        newParentId = teamU.getChildId();
                        this.teamRepository.delete(tChild);
                    } else {
                        teamUpdate = this.teamRepository.getTeamByChildId(team.get(i).getChildId());
                        Team teamU = teamUpdate.get();
                        teamU.setParentId(newParentId);
                        this.teamRepository.save(teamU);
                    }
                }
/*                Optional<Team> teamUpdate = this.teamRepository.getTeamByChildId(team.get(0).getChildId());
                Team teamU = teamUpdate.get();
                teamU.setParentId(tChild.getParentId());
                this.teamRepository.save(teamU);
                this.teamRepository.delete(tChild);
                resultMap.put(tChild.getParentId(), childMap);*/

            } else {
                for (int i = 0; i < parentIdWithTeam.size(); i++) {
                    Long parentIdWt = parentIdWithTeam.get(i);
                    Long childId = newParents.get(i);//0
                    Map<String, Long> childMap = new HashMap<>();
                    childMap.put("parentId", parentIdWt);
                    childMap.put("childId", childId);
                    resultMap.put(parentIdWt, childMap);
                }
                for (Team t : team) {
                    Map<String, Long> replaceMap = resultMap.get(t.getParentId());
                    if (replaceMap != null) {
                        t.setParentId(replaceMap.get("childId"));
                        this.teamRepository.save(t);
                    }
                }
            }

            this.clientRepository.deleteById(clientId);

            message = String.format("Cliente con dni %s ha sido eliminado y su equipo ha sido reemplazado.", client.getDni());
        }
        return message;
    }

    @Override
    public ClientsCountResource getClientsCount() {
        return this.clientRepository.getClientsCount();
    }

    public Map<Object, List<Team>> groupResultByParentId(List<Team> teamList){
        return teamList.stream()
                .sorted((f1, f2) -> ((Date)f1.getCreated_date()).compareTo(f2.getCreated_date()))
                .collect(Collectors.groupingBy(team -> team.getParentId()));
    }
}
