package com.leisure.entity.mapping;


import com.leisure.config.mapping.EnhancedModelMapper;
import com.leisure.entity.Sales;
import com.leisure.entity.dto.Sales.SalesResource;
import com.leisure.entity.dto.Sales.CreateSalesResource;
import com.leisure.entity.dto.Sales.UpdateSalesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class SalesMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public SalesResource toResource(Sales model){
        return mapper.map(model, SalesResource.class);
    }

    public Page<SalesResource> modelListToPage(List<Sales> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, SalesResource.class), pageable, modelList.size());
    }

    public List<SalesResource> modelListToList(List<Sales> modelList) {
        return mapper.mapList(modelList, SalesResource.class);
    }

    public Sales toModel(CreateSalesResource resource) {
        return mapper.map(resource, Sales.class);
    }

    public Sales toModel(UpdateSalesResource resource) {
        return mapper.map(resource, Sales.class);
    }


}
