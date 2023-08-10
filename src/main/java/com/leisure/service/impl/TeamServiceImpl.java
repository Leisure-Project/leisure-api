package com.leisure.service.impl;

import com.leisure.entity.Team;
import com.leisure.repository.TeamRepository;
import com.leisure.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
