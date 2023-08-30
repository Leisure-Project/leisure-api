package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Optional<User> getUserByDni(String dni);
}
