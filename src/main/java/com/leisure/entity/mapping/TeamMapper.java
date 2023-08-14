package com.leisure.entity.mapping;


import com.leisure.config.mapping.EnhancedModelMapper;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.TeamResource;
import com.leisure.entity.dto.Team.CreateTeamResource;
import com.leisure.entity.dto.Team.UpdateTeamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class TeamMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public TeamResource toResource(Team model){
        return mapper.map(model, TeamResource.class);
    }

    public Page<TeamResource> modelListToPage(List<Team> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, TeamResource.class), pageable, modelList.size());
    }

    public List<TeamResource> modelListToList(List<Team> modelList) {
        return mapper.mapList(modelList, TeamResource.class);
    }

    public Team toModel(CreateTeamResource resource) {
        return mapper.map(resource, Team.class);
    }

    public Team toModel(UpdateTeamResource resource) {
        return mapper.map(resource, Team.class);
    }

}
