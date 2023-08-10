package com.leisure.repository;

import com.leisure.entity.Client;
import com.leisure.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
