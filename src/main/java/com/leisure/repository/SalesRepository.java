package com.leisure.repository;

import com.leisure.entity.Sales;
import com.leisure.entity.dto.Sales.TotalSalesByClientResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales,Long> {
    @Query("SELECT s FROM Sales s WHERE s.created_date BETWEEN ?1 AND ?2")
    List<Sales> getAllSalesBetweenDates(Date startDate, Date endDate);
    @Query("SELECT s FROM Sales s WHERE s.client.id = ?1 AND s.created_date BETWEEN ?2 AND ?3")
    List<Sales> getAllSalesBetweenDatesAndClientId(Long clientId, Date startDate, Date endDate);
    @Query("SELECT SUM(s.price) AS total, s.client.id as client_id FROM Sales s WHERE s.client.id = ?1 GROUP BY s.client.id")
    TotalSalesByClientResource getTotalSalesByClient(Long clientId);
}
