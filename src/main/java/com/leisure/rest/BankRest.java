package com.leisure.rest;

import com.leisure.entity.Bank;
import com.leisure.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bank")
public class BankRest {
    private final BankService bankService;

    public BankRest(BankService bankService) {
        this.bankService = bankService;
    }
    @PostMapping(path = "/saveBank", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bank> saveBank(@RequestBody Bank b) throws Exception {
        Bank bank = this.bankService.create(b);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }
    @GetMapping(path = "/getBanks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Bank>> getBanks() throws Exception {
        List<Bank> bankList = this.bankService.getAll();
        return new ResponseEntity<>(bankList, HttpStatus.OK);
    }
    @GetMapping(path = "/getBankById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) throws Exception {
        Bank bank = this.bankService.getBankById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

}
