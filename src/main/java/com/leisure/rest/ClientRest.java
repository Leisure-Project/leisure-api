package com.leisure.rest;

import com.leisure.entity.Client;
import com.leisure.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/client")
public class ClientRest {
    @Autowired
    private ClientService clientService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Client> getClients(){
        return clientService.getAllClients();
    }
}
