package com.leisure.entity.dto.Sales;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateSalesResource {
    private String description;
    private Integer amount;
    private Double price;
    private String comments;
}
