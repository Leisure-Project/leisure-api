package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Admin;
import com.leisure.entity.Status;
import com.leisure.repository.AdminRepository;
import com.leisure.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin admin) throws Exception {
        Boolean existsByEmail = this.adminRepository.existsByEmail(admin.getEmail());
        Boolean existsByDni = this.adminRepository.existsByDni(admin.getDni());
        if(Boolean.TRUE.equals(existsByDni)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el dni %s", admin.getDni()));
        } else if(Boolean.TRUE.equals(existsByEmail)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el email %s", admin.getEmail()));
        }
        return this.adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin, Long adminId) throws Exception {
        Optional<Admin> adminOptional = this.adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new ResourceNotFoundException("Admin", adminId);
        }
        Admin adminUpdate = adminOptional.get();
        adminUpdate.setAlias(admin.getAlias());
        return this.adminRepository.save(adminUpdate);
    }


    @Override
    public List<Admin> getAllAdmins() throws Exception {
        List<Admin> adminList = this.adminRepository.findAll();
        if(adminList.isEmpty()){
            throw new RuntimeException("No se encontró ningún admin");
        }
        return adminList;
    }

    @Override
    public Admin getAdminById(Long adminId) {
        Optional<Admin> adminOptional = this.adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new ResourceNotFoundException("Admin", adminId);
        }
        return adminOptional.get();
    }
}
