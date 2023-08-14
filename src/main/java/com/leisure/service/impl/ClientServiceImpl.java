package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Client;
import com.leisure.entity.Status;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.StatusRepository;
import com.leisure.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatusRepository statusRepository;
    private final String ENTITY = "Cliente";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Client save(Client client, Long statusId) throws Exception {
        logger.error("a" + client);
        Optional<Status> status = this.statusRepository.findById(statusId);
        if(status.isEmpty()){
            throw new ResourceNotFoundException("Status", statusId);
        }
        client.setStatus(status.get());
        logger.error("n" + client);
        Boolean existsUsername = this.clientRepository.existsByUsername(client.getUsername());
        Boolean existsEmail = this.clientRepository.existsByEmail(client.getEmail());
        if(Boolean.TRUE.equals(existsUsername)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el username %s", client.getUsername()));
        } else if(Boolean.TRUE.equals(existsEmail)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el email %s", client.getEmail()));
        }

        return this.clientRepository.save(client);
    }

    @Override
    public Client update(Client client, Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, clientId);
        }
        Client clientUpdate = optionalClient.get();
        clientUpdate.setUsername(client.getUsername());
        clientUpdate.setPictureProfile(client.getPictureProfile());
        return this.clientRepository.save(clientUpdate);
    }

    @Override
    public Client getClientById(Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, clientId);
        }
        return optionalClient.get();
    }

    @Override
    public List<Client> getAllClients() throws Exception {
        List<Client> clientList = this.clientRepository.findAll();
        if(clientList.isEmpty()){
            throw new ResourceNotFoundException("No hay ningún cliente registrado en la plataforma");
        }
        return clientList;
    }
}
