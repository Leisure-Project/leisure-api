package com.leisure.service.impl;

import com.leisure.entity.PrincipalUser;
import com.leisure.entity.User;
import com.leisure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.getUserByDni(dni);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("No se encontró ningun usuario con dni %s", dni));
        }

        return PrincipalUser.build(optionalUser.get());
    }
}
