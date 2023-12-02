package com.leisure.rest;

import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.MembersTeamCountResource;
import com.leisure.entity.dto.Team.TeamResource;
import com.leisure.entity.dto.Team.CreateTeamResource;
import com.leisure.entity.dto.Team.UpdateTeamResource;
import com.leisure.entity.mapping.TeamMapper;
import com.leisure.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(value = "/api/team")
public class TeamRest {
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMapper mapper;
    @Autowired
    private ModelMapper mapping;
    @PostMapping(path = "/saveTeam", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TeamResource> saveTeam(@RequestBody CreateTeamResource resource) throws Exception {
        mapping.getConfiguration().setAmbiguityIgnored(true);
        Team team = this.teamService.save(mapping.map(resource, Team.class));
        TeamResource teamResource = mapper.toResource(team);
        return new ResponseEntity<>(teamResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateTeam/{teamId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TeamResource> updateTeam(@RequestBody UpdateTeamResource resource,
                                                   @PathVariable Long teamId) throws Exception {
        mapping.getConfiguration().setAmbiguityIgnored(true);
        Team team = this.teamService.update(mapping.map(resource, Team.class), teamId);
        TeamResource teamResource = mapper.toResource(team);
        return new ResponseEntity<>(teamResource, HttpStatus.OK);
    }

    @GetMapping(path = "/getTeamById/{teamId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TeamResource> getTeamById(@PathVariable Long teamId) throws Exception{
        mapping.getConfiguration().setAmbiguityIgnored(true);
        Team team = this.teamService.getTeamById(teamId);
        TeamResource teamResource = mapper.toResource(team);
        return new ResponseEntity<>(teamResource, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllTeams", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TeamResource>> getAllTeams() throws Exception{
        mapping.getConfiguration().setAmbiguityIgnored(true);
        List<Team> teamList = this.teamService.getAllTeams();
        List<TeamResource> teamResource = mapper.modelListToList(teamList);
        return new ResponseEntity<>(teamResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllTeamsGrouped", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map<Object, List<TeamResource>>> getAllTeamsGrouped() throws Exception{
        mapping.getConfiguration().setAmbiguityIgnored(true);
        Map<Object, List<TeamResource>> objectListMap = this.teamService.getTeams();
        return new ResponseEntity<>(objectListMap, HttpStatus.OK);
    }
    @GetMapping(path = "/getTeamsGroupedByParentId/{parentId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map<Object, List<TeamResource>>> getAllTeamsGrouped(@PathVariable Long parentId) throws Exception{
        mapping.getConfiguration().setAmbiguityIgnored(true);
        Map<Object, List<TeamResource>> objectListMap = this.teamService.getAllTeamsGrouped(parentId);
        return new ResponseEntity<>(objectListMap, HttpStatus.OK);
    }
    @GetMapping(path = "/getTeamHierarchy/{parentId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map<Integer, List<Map<Object, List<TeamResource>>>> > getTeamHierarchy(@PathVariable Long parentId) throws Exception{
        Map<Integer, List<Map<Object, List<TeamResource>>>>  objectListMap = this.teamService.getTeamHierarchy(parentId);
        return new ResponseEntity<>(objectListMap, HttpStatus.OK);
    }
    @GetMapping(path = "/getMembersCount", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<MembersTeamCountResource>> getMembersCount() throws Exception{
        List<MembersTeamCountResource>  list = this.teamService.getMembersCount();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
