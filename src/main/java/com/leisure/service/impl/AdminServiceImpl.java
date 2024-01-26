package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Admin;
import com.leisure.entity.Client;
import com.leisure.entity.Status;
import com.leisure.entity.enumeration.StatusName;
import com.leisure.repository.AdminRepository;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.StatusRepository;
import com.leisure.service.AdminService;
import com.leisure.service.AuthService;
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
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthService authService;

    @Override
    public Admin save(Admin admin) throws Exception {
        Boolean existsByEmail = this.adminRepository.existsByEmail(admin.getEmail());
        Boolean existsByDni = this.adminRepository.existsByDni(admin.getDni());
        if(Boolean.TRUE.equals(existsByDni)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el dni %s", admin.getDni()));
        } else if(Boolean.TRUE.equals(existsByEmail)){
            throw new RuntimeException(String.format("Ya existse un usuario registrado con el email %s", admin.getEmail()));
        }
        return this.authService.registerAdmin(admin);
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

    @Override
    public String changeClientStatus(Long clientId) {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("CLIENTE", clientId);
        }
        Client client = optionalClient.get();
        List<Status> status = this.statusRepository.findAll();
        if(StatusName.ACTIVO.equals(client.getStatus().getName())){
            client.setStatus(status.stream().filter(x -> x.getName().equals(StatusName.INACTIVO)).findFirst().get());
        } else {
            client.setStatus(status.stream().filter(x -> x.getName().equals(StatusName.ACTIVO)).findFirst().get());
        }
        this.clientRepository.save(client);

        return String.format("Se cambió el estado del cliente con DNI %s a %s", client.getDni(), client.getStatus().getName());
    }
}
