package com.leisure.rest;

import com.leisure.entity.dto.Jwt.LoginResource;
import com.leisure.entity.dto.Jwt.TokenResource;
import com.leisure.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthRest {
    @Autowired
    private AuthService authService;
    @PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenResource> login(@Valid @RequestBody LoginResource loginResource) throws Exception{
        TokenResource tokenResource = this.authService.login(loginResource);
        return new ResponseEntity<>(tokenResource, HttpStatus.OK);
    }
    @GetMapping(path = "/isAdmin/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Boolean isAdminByUserId(@PathVariable Long userId) throws Exception{
        return this.authService.isAdmin(userId);
    }

    @GetMapping(path = "/isAdminByDni/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Boolean isAdminByDni(@PathVariable String dni) throws Exception{
        return this.authService.isAdminByDni(dni);
    }
}
