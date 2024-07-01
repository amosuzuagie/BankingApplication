package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.repository.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class BankAccountService {
    private final BankAccountRepository accountRepository;

    public BankAccountService (BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<BankAccount> getAllAccount () {
        return accountRepository.findAll();
    }

    public BankAccount getAccountById (long id) {
        return accountRepository.findById(id).get();


    }

    public BankAccount getAccountByUser (AccountUser user) {
        return accountRepository.findByUser(user);
    }

    public BankAccount getAccountByAccountNumber (String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public BankAccount openBankAccount ( double balance, AccountUser user) {
        String accountNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(accountNumber, balance, user);
        return accountRepository.save(newAccount);
    }

    public BankAccount openBankAccount ( AccountUser user) {
        String accountNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(accountNumber, 0.0, user);
        return accountRepository.save(newAccount);
    }

    private String generateAccountNumber () {
        StringBuilder accountNumber = new StringBuilder();
        int count = 0;
        while (count<10) {
            int random = new Random().nextInt(10);
            accountNumber.append(random);
            count++;
        }
        return accountNumber.toString();
    }
}
