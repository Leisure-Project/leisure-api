package com.leisure.service;


import com.leisure.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    String changeUserEmail(String email, Long userId);
    String changeUserPassword(String password, Long userId);
}
