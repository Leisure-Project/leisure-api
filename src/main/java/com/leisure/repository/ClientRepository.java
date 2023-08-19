package com.leisure.repository;

import com.leisure.entity.Client;
import com.leisure.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Boolean existsByDni(String dni);
    Boolean existsByEmail(String email);
    Optional<Client> findByDni(String dni);
    @Query("SELECT c FROM Client c WHERE c.status.id = ?1")
    List<Client> getAllClientsByStatusId(Long statusId);
}
