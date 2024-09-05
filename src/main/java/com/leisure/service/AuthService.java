package com.leisure.service;

import com.leisure.entity.Admin;
import com.leisure.entity.Client;
import com.leisure.entity.Role;
import com.leisure.entity.dto.Jwt.LoginResource;
import com.leisure.entity.dto.Jwt.TokenResource;

public interface AuthService {
    Role getClientRole() throws Exception;
    Role getAdminRole() throws Exception;
    Client registerClient(Client request) throws Exception;
    Admin registerAdmin(Admin request) throws Exception;
    TokenResource login(LoginResource loginResource) throws Exception;

    Boolean isAdmin(Long userId);
    Boolean isAdminByDni(String dni);
}
