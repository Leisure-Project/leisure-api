package com.leisure.service;

import com.leisure.entity.Client;

import java.util.List;

public interface ClientService {
    Client save(Client client, Long statusId) throws Exception;
    Client saveClientInDbAndTeam(Client client, Long parentId, Long statusId) throws Exception;
    Client update(Client client, Long clientId) throws Exception;
    Client getClientById(Long clientId) throws Exception;
    Client getClientByUsername(Long dni) throws Exception;
    List<Client> getAllClients() throws Exception;
}
