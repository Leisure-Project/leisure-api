package com.leisure.rest;

import com.leisure.config.exception.ForbiddenAccessException;
import com.leisure.entity.Admin;
import com.leisure.entity.Admin;
import com.leisure.entity.Admin;
import com.leisure.entity.dto.Admin.AdminResource;
import com.leisure.entity.dto.Admin.CreateAdminResource;
import com.leisure.entity.dto.Admin.UpdateAdminResource;
import com.leisure.entity.mapping.AdminMapper;
import com.leisure.repository.AdminRepository;
import com.leisure.service.AdminService;
import com.leisure.util.RequestUtil;
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
    @Autowired
    private RequestUtil requestUtil;
    @Autowired
    private AdminRepository adminRepository;
    @PostMapping(path = "/saveAdmin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> saveAdmin(@RequestBody CreateAdminResource resource) throws Exception {
        if(this.adminRepository.count() > 0 && (!this.requestUtil.isAdmin())) throw new ForbiddenAccessException();
        Admin admin = this.adminService.save(mapping.map(resource, Admin.class));
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllAdmins", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<AdminResource>> getAllAdmins() throws Exception{
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        List<Admin> adminList = this.adminService.getAllAdmins();
        List<AdminResource> adminResource = mapper.modelListToList(adminList);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAdminById/{adminId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> getAdminById(@PathVariable Long adminId) throws Exception{
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        Admin admin = this.adminService.getAdminById(adminId);
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAdminByDni/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> getAdminByDni(@PathVariable String dni) throws Exception{
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        Admin admin = this.adminService.getAdminByDni(dni);
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateAdmin/{adminId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AdminResource> updateAdmin(@RequestBody UpdateAdminResource resource,
                                                     @PathVariable Long adminId) throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        Admin admin = this.adminService.update(mapping.map(resource, Admin.class), adminId);
        AdminResource adminResource = mapping.map(admin, AdminResource.class);
        return new ResponseEntity<>(adminResource, HttpStatus.OK);
    }
    @PostMapping(path = "/changeClientStatus/{clientId}", produces = { MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changeClientStatus(@PathVariable Long clientId) throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        return new ResponseEntity<>(this.adminService.changeClientStatus(clientId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteClient/{clientId}", produces = { MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        return new ResponseEntity<>(this.adminService.deleteClient(clientId), HttpStatus.OK);
    }

}
