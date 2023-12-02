package com.leisure.entity.dto.Team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersTeamCountResource {
    private Long parentId;
    private Long teamMembers;

    public MembersTeamCountResource(Long parentId, Long teamMembers) {
        this.parentId = parentId;
        this.teamMembers = teamMembers;
    }
}
