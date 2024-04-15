package com.leisure.service;


import com.leisure.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserByDni(String dni);
    String changeUserEmail(String email, Long userId);
    String changeUserPassword(String oldPassword, String newPassword, Long userId);
}
