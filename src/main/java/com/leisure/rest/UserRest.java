package com.leisure.rest;

import com.leisure.entity.Team;
import com.leisure.entity.User;
import com.leisure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userService.getAllUsers();
    }
}
