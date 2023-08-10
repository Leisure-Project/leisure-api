package com.leisure.rest;

import com.leisure.entity.Role;
import com.leisure.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleRest {
    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }
}
