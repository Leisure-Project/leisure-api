package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Boolean existsByChildId(Long childId);
    Boolean existsByParentId(Long parentId);
    List<Team> getTeamsByParentId(Long parentId);
    @Query(value = "SELECT t FROM Team t WHERE t.parentId IN (?1)")
    List<Team> getAllTeamByParentIdList(List<Long> parentsId) throws Exception;
}
