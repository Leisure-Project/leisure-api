package com.leisure.repository;

import com.leisure.entity.Role;
import com.leisure.entity.Status;
import com.leisure.entity.enumeration.Rolname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Rolname rolname);
    boolean existsByName(Rolname rolname);
}
