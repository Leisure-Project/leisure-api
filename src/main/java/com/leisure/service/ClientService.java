package com.leisure.service;

import com.leisure.entity.Client;

import java.util.List;

public interface ClientService {
    Client save(Client client) throws Exception;
    Client update(Client client, Long clientId) throws Exception;
    Client getClientById(Long clientId) throws Exception;
    List<Client> getAllClients() throws Exception;

}
