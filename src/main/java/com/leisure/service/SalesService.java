package com.leisure.service;

import com.leisure.entity.Sales;
import com.leisure.entity.dto.Sales.TotalSalesByClientResource;

import java.util.Date;
import java.util.List;

public interface SalesService {
    Sales save(Sales admin, Long clientId) throws Exception;

    Sales update(Sales admin, Long salesId) throws Exception;

    List<Sales> getAllSales() throws Exception;

    Sales getSalesById(Long salesId);
    List<Sales> getAllSalesBetweenDates(String startDate, String endDate) throws Exception;
    List<Sales> getAllSalesBetweenDatesAndClientId(Long clientId, String startDate, String endDate) throws Exception;
    TotalSalesByClientResource getTotalSalesByClient(Long clientId) throws Exception;

}
