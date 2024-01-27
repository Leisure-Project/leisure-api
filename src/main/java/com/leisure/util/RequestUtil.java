package com.leisure.util;


import com.leisure.config.security.JwtProvider;
import com.leisure.entity.User;
import com.leisure.entity.enumeration.Rolname;
import com.leisure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUtil {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtProvider provider;
    @Autowired
    private UserRepository usersRepository;
    public String getUserToken(){
        return request.getHeader("Authorization").substring(7);
    }
    public Long getUserId(){
        return this.provider.getUserIdFromToken(getUserToken());
    }
    public Boolean isAdmin(){
        User user = this.usersRepository.findById(this.getUserId()).get();
        return user.getRoles().stream().anyMatch(r -> r.getName().name().equals(Rolname.Role_Admin.name()));
    }
}
