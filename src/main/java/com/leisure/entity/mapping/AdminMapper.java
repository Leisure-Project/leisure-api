package com.leisure.entity.mapping;


import com.leisure.config.mapping.EnhancedModelMapper;
import com.leisure.entity.Admin;
import com.leisure.entity.dto.Admin.AdminResource;
import com.leisure.entity.dto.Admin.CreateAdminResource;
import com.leisure.entity.dto.Admin.UpdateAdminResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class AdminMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public AdminResource toResource(Admin model){
        return mapper.map(model, AdminResource.class);
    }

    public Page<AdminResource> modelListToPage(List<Admin> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, AdminResource.class), pageable, modelList.size());
    }

    public List<AdminResource> modelListToList(List<Admin> modelList) {
        return mapper.mapList(modelList, AdminResource.class);
    }

    public Admin toModel(CreateAdminResource resource) {
        return mapper.map(resource, Admin.class);
    }

    public Admin toModel(UpdateAdminResource resource) {
        return mapper.map(resource, Admin.class);
    }

}
