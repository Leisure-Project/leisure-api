package com.leisure.rest;

import com.leisure.entity.Team;
import com.leisure.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/team")
public class TeamRest {
    @Autowired
    private TeamService teamService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }
}
