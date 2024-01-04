package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.MembersTeamCountResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Boolean existsByChildId(Long childId);
    Boolean existsByParentId(Long parentId);
    List<Team> getTeamsByParentId(Long parentId);
    @Query(value = "SELECT t FROM Team t WHERE t.parentId IN (?1)")
    List<Team> getAllTeamByParentIdList(List<Long> parentsId) throws Exception;
    @Query(value = "SELECT new com.leisure.entity.dto.Team.MembersTeamCountResource(t.parentId, count(t.childId)) " +
            "FROM Team t JOIN Client c ON t.childId = c.id WHERE c.status.name = 'ACTIVO' " +
            "GROUP BY t.parentId")
    List<MembersTeamCountResource> getMembersCount();

    @Query(value = "SELECT new com.leisure.entity.dto.Team.MembersTeamCountResource(t.parentId, count(t.childId)) " +
            "FROM Team t JOIN Client c ON t.childId = c.id WHERE c.status.name = 'ACTIVO' and t.parentId = ?1 " +
            "GROUP BY t.parentId")
    MembersTeamCountResource getMembersCountByParentId(Long parentId);
}
