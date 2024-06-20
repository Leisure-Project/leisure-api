package com.leisure.rest;

import com.leisure.config.exception.ForbiddenAccessException;
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
@RequestMapping(value = "/api/health-check")
public class HealthCheckRest {

    @GetMapping(path = "")
    public ResponseEntity<String> healthcheck()  {
        return ResponseEntity.ok("Beizzon status 200");
    }
}
