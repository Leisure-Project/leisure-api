package com.leisure.rest;

import com.leisure.entity.Sales;
import com.leisure.entity.dto.Sales.SalesResource;
import com.leisure.entity.dto.Sales.CreateSalesResource;
import com.leisure.entity.dto.Sales.TotalSalesByClientResource;
import com.leisure.entity.dto.Sales.UpdateSalesResource;
import com.leisure.entity.mapping.ClientMapper;
import com.leisure.entity.mapping.SalesMapper;
import com.leisure.service.SalesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/api/sales")
public class SalesRest {
    @Autowired
    private SalesService salesService;
    @Autowired
    private SalesMapper mapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ModelMapper mapping;
    @PostMapping(path = "/saveSales/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SalesResource> saveSales(@RequestBody CreateSalesResource resource,
                                                   @PathVariable Long clientId) throws Exception {
        Sales sales = this.salesService.save(mapping.map(resource, Sales.class), clientId);
        SalesResource salesResource = mapping.map(sales, SalesResource.class);
        salesResource.setClientResource(clientMapper.toResource(sales.getClient()));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllSaless", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SalesResource>> getAllSaless() throws Exception{
        List<Sales> salesList = this.salesService.getAllSales();
        List<SalesResource> salesResource = mapper.modelListToList(salesList);
        IntStream.range(0, salesResource.size()).forEach(i -> salesResource.get(i).setClientResource(clientMapper.toResource(salesList.get(i).getClient())));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getSalesById/{salesId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SalesResource> getSalesById(@PathVariable Long salesId) throws Exception{
        Sales sales = this.salesService.getSalesById(salesId);
        SalesResource salesResource = mapping.map(sales, SalesResource.class);
        salesResource.setClientResource(clientMapper.toResource(sales.getClient()));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @PostMapping(path = "/updateSales/{salesId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SalesResource> updateSales(@RequestBody UpdateSalesResource resource,
                                                       @PathVariable Long salesId) throws Exception {
        Sales sales = this.salesService.update(mapping.map(resource, Sales.class), salesId);
        SalesResource salesResource = mapping.map(sales, SalesResource.class);
        salesResource.setClientResource(clientMapper.toResource(sales.getClient()));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllSalesBetweenDates/{startDate}/{endDate}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SalesResource>> getAllSalesBetweenDates(@PathVariable String startDate,
                                                                       @PathVariable String endDate) throws Exception{
        List<Sales> salesList = this.salesService.getAllSalesBetweenDates(startDate, endDate);
        List<SalesResource> salesResource = mapper.modelListToList(salesList);
        IntStream.range(0, salesResource.size()).forEach(i -> salesResource.get(i).setClientResource(clientMapper.toResource(salesList.get(i).getClient())));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getAllSalesBetweenDatesAndClientId/{clientId}/{startDate}/{endDate}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SalesResource>> getAllSalesBetweenDatesAndClientId(@PathVariable Long clientId,
                                                                                  @PathVariable String startDate,
                                                                                  @PathVariable String endDate) throws Exception{
        List<Sales> salesList = this.salesService.getAllSalesBetweenDatesAndClientId(clientId, startDate, endDate);
        List<SalesResource> salesResource = mapper.modelListToList(salesList);
        IntStream.range(0, salesResource.size()).forEach(i -> salesResource.get(i).setClientResource(clientMapper.toResource(salesList.get(i).getClient())));
        return new ResponseEntity<>(salesResource, HttpStatus.OK);
    }
    @GetMapping(path = "/getTotalSalesByClient/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TotalSalesByClientResource> getTotalSalesByClient(@PathVariable Long clientId) throws Exception{
        TotalSalesByClientResource totalSalesByClientResources = this.salesService.getTotalSalesByClient(clientId);
        return new ResponseEntity<>(totalSalesByClientResources, HttpStatus.OK);
    }
}
