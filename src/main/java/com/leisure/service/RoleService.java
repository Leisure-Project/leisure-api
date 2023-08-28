package com.leisure.service;

import com.leisure.entity.Role;
import com.leisure.entity.enumeration.Rolname;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> findByName(Rolname rolname);
    void seed();

}
