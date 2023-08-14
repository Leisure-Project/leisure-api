package com.leisure.entity.mapping;


import com.leisure.config.mapping.EnhancedModelMapper;
import com.leisure.entity.User;
import com.leisure.entity.dto.User.CreateUserResource;
import com.leisure.entity.dto.User.UserResource;
import com.leisure.entity.dto.User.UpdateUserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class UserMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public UserResource toResource(User model){
        return mapper.map(model, UserResource.class);
    }

    public Page<UserResource> modelListToPage(List<User> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, UserResource.class), pageable, modelList.size());
    }

    public List<UserResource> modelListToList(List<User> modelList) {
        return mapper.mapList(modelList, UserResource.class);
    }

    public User toModel(CreateUserResource resource) {
        return mapper.map(resource, User.class);
    }

    public User toModel(UpdateUserResource resource) {
        return mapper.map(resource, User.class);
    }

}
