package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.enumeration.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Long> {
    Status getStatusByName(String name);
    boolean existsByName(StatusName statusName);

}
