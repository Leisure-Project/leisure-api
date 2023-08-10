package com.leisure.rest;

import com.leisure.entity.Admin;
import com.leisure.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminRest {
    @Autowired
    private AdminService adminService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Admin> getAdmins(){
        return adminService.getAllAdmins();
    }
}
