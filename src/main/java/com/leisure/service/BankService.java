package com.leisure.service;

import com.leisure.entity.Bank;

import java.util.List;

public interface BankService {
    Bank create(Bank bank);
    List<Bank> getAll();
    Bank getBankById(Long id);
}
