package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.User;
import com.leisure.repository.UserRepository;
import com.leisure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByDni(String dni) {
        Optional<User> optionalUser = this.userRepository.getUserByDni(dni);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", dni);
        }
        return optionalUser;
    }

    @Override
    public String changeUserEmail(String email, Long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", userId);
        }
        User user = optionalUser.get();
        user.setEmail(email);

        Boolean existsByEmail = this.userRepository.existsByEmail(user.getEmail());
        if(!Boolean.TRUE.equals(existsByEmail)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el email %s", email));
        }
        this.userRepository.save(user);
        return String.format("Se actualizó el email a %s", email);
    }

    @Override
    public String changeUserPassword(String password, Long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Usuario", userId);
        }
        User user = optionalUser.get();
        user.setPassword(password);
        this.userRepository.save(user);
        return "Se actualizó correctamente la contraseña";
    }
}
