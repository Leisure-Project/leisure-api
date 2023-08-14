package com.leisure.rest;

import com.leisure.entity.Client;
import com.leisure.entity.dto.Client.ClientResource;
import com.leisure.entity.dto.Client.CreateClientResource;
import com.leisure.entity.dto.Client.UpdateClientResource;
import com.leisure.entity.mapping.ClientMapper;
import com.leisure.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/client")
public class ClientRest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientMapper mapper;
    @Autowired
    private ModelMapper mapping;

    @PostMapping(path = "/saveClient", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> saveClient(@RequestBody CreateClientResource resource) throws Exception {
        Client client = this.clientService.save(mapping.map(resource, Client.class));
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateClient/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> updateClient(@RequestBody UpdateClientResource resource,
                                                       @PathVariable Long clientId) throws Exception {
        Client client = this.clientService.update(mapping.map(resource, Client.class), clientId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }

    @GetMapping(path = "/getClientById/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> getClientById(@PathVariable Long clientId) throws Exception{
        Client client = this.clientService.getClientById(clientId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllClients", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClientResource>> getAllClients() throws Exception{
        List<Client> clientList = this.clientService.getAllClients();
        List<ClientResource> clientResource = mapper.modelListToList(clientList);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }

}
