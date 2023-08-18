package com.leisure.entity.dto.Sales;

import com.leisure.entity.dto.Client.ClientResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SalesResource {
    private Long id;
    private String description;
    private Integer amount;
    private Double price;
    private String comments;
    private Date created_date;
    private Boolean isActive;
    private ClientResource clientResource;
}
