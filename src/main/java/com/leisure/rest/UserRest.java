package com.leisure.rest;

import com.leisure.entity.Team;
import com.leisure.entity.User;
import com.leisure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/updateEmail/{email}/{userId}")
    public ResponseEntity<String> updateEmail(@PathVariable String email, @PathVariable Long userId) throws Exception{
        String message = this.userService.changeUserEmail(email, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping(path = "/changePassword/{password}/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable String password, @PathVariable Long userId) throws Exception{
        String message = this.userService.changeUserPassword(password, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
