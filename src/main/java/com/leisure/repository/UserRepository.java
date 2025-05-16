package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByDni(String dni);
    Optional<User> getUserByDni(String dni);
    @Query("SELECT u.id FROM User  u WHERE u.dni = ?1")
    Long getUserIdByDni(String dni);
}
