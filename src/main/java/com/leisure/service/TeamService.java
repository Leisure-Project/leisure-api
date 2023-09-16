package com.leisure.service;

import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.TeamResource;

import java.util.List;
import java.util.Map;

public interface TeamService {
    Team save(Team team) throws Exception;
    Team update(Team team, Long teamId) throws Exception;
    Team getTeamById(Long teamId) throws Exception;
    List<Team> getAllTeams() throws Exception;

    Map<Object, List<TeamResource>> getTeams() throws Exception;
    Map<Object, List<TeamResource>> getAllTeamsGrouped(Long parentId) throws Exception;
    Map<Integer, List<Map<Object, List<TeamResource>>>>  getTeamHierarchy(Long parentId) throws Exception;
}
