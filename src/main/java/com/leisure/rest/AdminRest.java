package com.leisure.rest;

import com.leisure.entity.Admin;
import com.leisure.entity.Admin;
import com.leisure.entity.Admin;
import com.leisure.entity.dto.Admin.AdminResource;
import com.leisure.entity.dto.Admin.CreateAdminResource;
import com.leisure.entity.dto.Admin.UpdateAdminResource;
import com.leisure.entity.mapping.AdminMapper;
import com.leisure.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminRest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper mapper;
    @Autowired
    private ModelMapper mapping;
    @PostMapping(path = "/saveAdmin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> saveAdmin(@RequestBody CreateAdminResource resource) throws Exception {
        Admin admin = this.adminService.save(mapping.map(resource, Admin.class));
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllAdmins", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<AdminResource>> getAllAdmins() throws Exception{
        List<Admin> adminList = this.adminService.getAllAdmins();
        List<AdminResource> adminResource = mapper.modelListToList(adminList);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAdminById/{adminId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> getAdminById(@PathVariable Long adminId) throws Exception{
        Admin admin = this.adminService.getAdminById(adminId);
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateAdmin/{adminId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> updateAdmin(@RequestBody UpdateAdminResource resource,
                                                       @PathVariable Long adminId) throws Exception {
        Admin admin = this.adminService.update(mapping.map(resource, Admin.class), adminId);
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @PostMapping(path = "/changeClientStatus/{clientId}", produces = { MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changeClientStatus(@PathVariable Long clientId) throws Exception {
        return new ResponseEntity<>(this.adminService.changeClientStatus(clientId), HttpStatus.OK);
    }

}
