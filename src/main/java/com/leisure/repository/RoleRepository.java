package com.leisure.repository;

import com.leisure.entity.Role;
import com.leisure.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
