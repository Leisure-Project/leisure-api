package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.MembersTeamCountResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Boolean existsByChildId(Long childId);
    Boolean existsByParentId(Long parentId);
    Optional<Team> getTeamByChildId(Long childId);
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
    @Query("select count(t) from Team t where t.parentId = ?1")
    Long getMemberCountByParent(Long parentId);
    void deleteByChildId(Long childId);
    void deleteAllByChildId(List<Long> childId);
    Team getTeamByParentIdAndChildId(Long parentId, Long childId);
    Optional<Team> getOptTeamByParentIdAndChildId(Long parentId, Long childId);

    @Query(value = "SELECT t FROM Team t WHERE t.parentId = t.childId")
    List<Team> getAllTeamsDuplicates();
}
