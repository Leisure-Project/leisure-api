package com.leisure.entity.dto.Sales;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateSalesResource {
    private String description;
    private Integer amount;
    private Double price;
    private String comments;
    private Date created_date;
    private Boolean isActive;
}
