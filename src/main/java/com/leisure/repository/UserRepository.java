package com.leisure.repository;

import com.leisure.entity.Status;
import com.leisure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
