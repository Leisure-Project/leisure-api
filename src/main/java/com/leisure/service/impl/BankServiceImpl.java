package com.leisure.service.impl;

import com.leisure.entity.Bank;
import com.leisure.repository.BankRepository;
import com.leisure.service.BankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final String ENTITY = "Banco";

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public Bank create(Bank bank) {
        return this.bankRepository.save(bank);
    }

    @Override
    public List<Bank> getAll() {
        return this.bankRepository.findAll();
    }

    @Override
    public Bank getBankById(Long id) {
        return this.bankRepository.findById(id).get();
    }
}
