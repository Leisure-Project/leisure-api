package com.leisure.repository;

import com.leisure.entity.Admin;
import com.leisure.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByDni(String dni);
}
