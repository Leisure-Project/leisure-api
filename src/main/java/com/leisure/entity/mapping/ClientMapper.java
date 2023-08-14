package com.leisure.entity.mapping;


import com.leisure.config.mapping.EnhancedModelMapper;
import com.leisure.entity.Client;
import com.leisure.entity.dto.Client.ClientResource;
import com.leisure.entity.dto.Client.CreateClientResource;
import com.leisure.entity.dto.Client.UpdateClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ClientMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public ClientResource toResource(Client model){
        return mapper.map(model, ClientResource.class);
    }

    public Page<ClientResource> modelListToPage(List<Client> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ClientResource.class), pageable, modelList.size());
    }

    public List<ClientResource> modelListToList(List<Client> modelList) {
        return mapper.mapList(modelList, ClientResource.class);
    }

    public Client toModel(CreateClientResource resource) {
        return mapper.map(resource, Client.class);
    }

    public Client toModel(UpdateClientResource resource) {
        return mapper.map(resource, Client.class);
    }

}
