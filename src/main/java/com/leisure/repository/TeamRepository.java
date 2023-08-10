package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
