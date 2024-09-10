package com.leisure.service;

import com.leisure.entity.Admin;
import com.leisure.entity.dto.Client.ClientsCountResource;

import java.util.List;

public interface AdminService {
    Admin save(Admin admin) throws Exception;
    Admin update(Admin admin, Long adminId) throws Exception;
    List<Admin> getAllAdmins() throws Exception;
    Admin getAdminById(Long adminId);
    String changeClientStatus(Long clientId);

    Admin getAdminByDni(String dni);

    String deleteClient(Long clientId);
    ClientsCountResource getClientsCount();
}
