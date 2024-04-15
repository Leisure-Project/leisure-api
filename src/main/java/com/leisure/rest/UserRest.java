package com.leisure.rest;

import com.leisure.config.exception.ForbiddenAccessException;
import com.leisure.entity.Team;
import com.leisure.entity.User;
import com.leisure.service.UserService;
import com.leisure.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUtil requestUtil;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getUsers(){
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        return userService.getAllUsers();
    }

    @PostMapping(path = "/updateEmail/{email}/{userId}")
    public ResponseEntity<String> updateEmail(@PathVariable String email, @PathVariable Long userId) throws Exception{
        this.validate(userId);
        String message = this.userService.changeUserEmail(email, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping(path = "/changePassword/{oldPassword}/{newPassword}/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable String oldPassword,
                                                 @PathVariable String newPassword,
                                                 @PathVariable Long userId) throws Exception{
        this.validate(userId);
        String message = this.userService.changeUserPassword(oldPassword, newPassword, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    private Boolean validate(Long clientId) {
        Boolean e = false;
        if(this.requestUtil.isAdmin()) {
            return true;
        } else if (Objects.equals(clientId, this.requestUtil.getUserId())) {
            return true;
        } else {
            throw new ForbiddenAccessException();
        }
    }
}
