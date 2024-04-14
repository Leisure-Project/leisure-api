package com.leisure.rest;

import com.leisure.config.exception.ForbiddenAccessException;
import com.leisure.entity.Client;
import com.leisure.entity.dto.Client.ClientResource;
import com.leisure.entity.dto.Client.CreateClientResource;
import com.leisure.entity.dto.Client.UpdateClientResource;
import com.leisure.entity.mapping.ClientMapper;
import com.leisure.service.ClientService;
import com.leisure.util.RequestUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/client")
public class ClientRest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientMapper mapper;
    @Autowired
    private ModelMapper mapping;
    @Autowired
    private RequestUtil requestUtil;

    @PostMapping(path = "/saveClient/{statusId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> saveClient(@RequestBody CreateClientResource resource,
                                                     @PathVariable Long statusId) throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        Client client = this.clientService.save(mapping.map(resource, Client.class), statusId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }
    @PostMapping(path = "/saveClientAndTeam/{parentId}/{statusId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> saveClientAndTeam(@RequestBody CreateClientResource resource,
                                                     @PathVariable Long statusId, @PathVariable Long parentId) throws Exception {
        Client client = this.clientService.saveClientInDbAndTeam(mapping.map(resource, Client.class), parentId, statusId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateClient/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> updateClient(@RequestBody UpdateClientResource resource,
                                                       @PathVariable Long clientId) throws Exception {
        //this.validate(clientId);
        Client client = this.clientService.update(mapping.map(resource, Client.class), clientId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }



    @GetMapping(path = "/getClientById/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> getClientById(@PathVariable Long clientId) throws Exception{
        //this.validate(clientId);
        Client client = this.clientService.getClientById(clientId);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getClientByDni/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ClientResource> getClientByDni(@PathVariable String dni) throws Exception{
        Client client = this.clientService.getClientByDni(dni);
        ClientResource clientResource = mapping.map(client, ClientResource.class);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllClients", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClientResource>> getAllClients() throws Exception{
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        List<Client> clientList = this.clientService.getAllClients();
        List<ClientResource> clientResource = mapper.modelListToList(clientList);
        return new ResponseEntity<>(clientResource, HttpStatus.OK);
    }

    @PostMapping(path = "/resetClients")
    public ResponseEntity<String> resetClients() throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        String message = this.clientService.resetClients();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping(path = "/verifyClientsStatus")
    public ResponseEntity<List<String>> verifyClientsStatus() throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        List<String>  messageList = this.clientService.verifyClientsStatus();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
    @PostMapping(path = "/calculateEarnings")
    public ResponseEntity<List<String>> calculateEarnings() throws Exception {
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        List<String>  messageList = this.clientService.calculateEarnings();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
    @PostMapping(path = "/updateBonus")
    public ResponseEntity<String> updateBonus() throws Exception {
        return new ResponseEntity<>(this.clientService.updateBonus(), HttpStatus.OK);
    }


    private Boolean validate(Long clientId) {
        Boolean e = false;
        if(this.requestUtil.isAdmin()) {
            return true;
        } else if (Objects.equals(clientId, this.requestUtil.getUserId())) {
            return true;
        } else {
            throw new ForbiddenAccessException();
        }
    }
}
