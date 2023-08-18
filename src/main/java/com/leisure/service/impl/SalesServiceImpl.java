package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Client;
import com.leisure.entity.Sales;
import com.leisure.entity.dto.Sales.TotalSalesByClientResource;
import com.leisure.entity.mapping.ClientMapper;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.SalesRepository;
import com.leisure.service.SalesService;
import com.leisure.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Sales save(Sales sales, Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Cliente", clientId);
        }
        sales.setClient(optionalClient.get());
        return this.salesRepository.save(sales);
    }

    @Override
    public Sales update(Sales sales, Long salesId) throws Exception {
        Optional<Sales> salesOptional = this.salesRepository.findById(salesId);
        if(salesOptional.isEmpty()){
            throw new ResourceNotFoundException("Venta", salesId);
        }
        Sales salesUpdate = salesOptional.get();
        salesUpdate.setDescription(sales.getDescription());
        salesUpdate.setAmount(sales.getAmount());
        salesUpdate.setPrice(sales.getPrice());
        salesUpdate.setComments(sales.getComments());
        return this.salesRepository.save(salesUpdate);
    }


    @Override
    public List<Sales> getAllSales() throws Exception {
        List<Sales> salesList = this.salesRepository.findAll();
        if(salesList.isEmpty()){
            throw new RuntimeException("No se encontró ninguna venta");
        }
        return salesList;
    }

    @Override
    public Sales getSalesById(Long salesId) {
        Optional<Sales> salesOptional = this.salesRepository.findById(salesId);
        if(salesOptional.isEmpty()){
            throw new ResourceNotFoundException("Venta", salesId);
        }
        return salesOptional.get();
    }

    @Override
    public List<Sales> getAllSalesBetweenDates(String startDate, String endDate) throws Exception {

        List<Sales> salesList = this.salesRepository.getAllSalesBetweenDates(DateUtils.convertStringToDate(startDate), DateUtils.convertStringToDate(endDate));
        if(salesList.isEmpty()){
            throw new ResourceNotFoundException(String.format("No se encontró ninguna venta entre las fechas %s - %s", startDate, endDate));
        }
        return salesList;
    }

    @Override
    public List<Sales> getAllSalesBetweenDatesAndClientId(Long clientId, String startDate, String endDate) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Cliente", clientId);
        }
        List<Sales> salesList = this.salesRepository.getAllSalesBetweenDatesAndClientId(clientId, DateUtils.convertStringToDate(startDate), DateUtils.convertStringToDate(endDate));
        if(salesList.isEmpty()){
            throw new ResourceNotFoundException(String.format("No se encontró ninguna venta del usuario entre las fechas %s - %s", startDate, endDate));
        }
        return salesList;
    }

    @Override
    public TotalSalesByClientResource getTotalSalesByClient(Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Cliente", clientId);
        }
        TotalSalesByClientResource totalSalesByClientResources = this.salesRepository.getTotalSalesByClient(clientId);
        return totalSalesByClientResources;
    }
}
