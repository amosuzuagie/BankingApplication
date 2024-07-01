package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.entity.Transactions;
import com.mstramohz.BankingApplication.enums.TransactionType;
import com.mstramohz.BankingApplication.exception.TransactionException;
import com.mstramohz.BankingApplication.repository.TransactionsRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankingOperationService {
    private final MailService mailService;
    private final BankAccountService bankAccountService;
    private final TransactionsRepository transactionsRepository;

    @Autowired
    public BankingOperationService (BankAccountService bankAccountService,
                                    TransactionsRepository transactionsRepository,
                                    MailService mailService
    ) {
        this.mailService = mailService;
        this.bankAccountService = bankAccountService;
        this.transactionsRepository = transactionsRepository;
    }

    public void depositFund (String accountNumber, double amount, String transactionId) throws MessagingException {
        if (amount < 0) throw new TransactionException("Invalid amount.");

        BankAccount dbAccount = bankAccountService.getAccountByAccountNumber(accountNumber);
        assert dbAccount != null;

        dbAccount.setAccountBalance(dbAccount.getAccountBalance() + amount);
        AccountUser user = dbAccount.getUser();
        mailService.depositNotification(user.getUsername(), user.getFirstname(), amount);

        Transactions txn = new Transactions();
        txn.setAmount(amount);
        txn.setTransactionId(transactionId);
        txn.setAccountNumber(accountNumber);
        txn.setType(TransactionType.DEPOSIT);
        transactionsRepository.save(txn);
    }

    public BankAccount depositFund (String accountNumber, double amount) throws MessagingException {
        if (amount < 0) throw new TransactionException("Invalid amount.");

        BankAccount dbAccount = bankAccountService.getAccountByAccountNumber(accountNumber);
        assert dbAccount != null;

        dbAccount.setAccountBalance(dbAccount.getAccountBalance() + amount);
        AccountUser user = dbAccount.getUser();
        mailService.depositNotification(user.getUsername(), user.getFirstname(), amount);

        Transactions txn = new Transactions();
        txn.setAmount(amount);
        txn.setAccountNumber(accountNumber);
        txn.setType(TransactionType.DEPOSIT);
        txn.setTransactionId(generateTnxId());
        transactionsRepository.save(txn);

        return dbAccount;
    }

    public void withdrawFund (String accountNumber, double amount, String transactionId) throws MessagingException {
        BankAccount dbAccount = bankAccountService.getAccountByAccountNumber(accountNumber);
        assert dbAccount != null;

        if (dbAccount.getAccountBalance() < amount) throw new TransactionException("Insufficient amount.");
        dbAccount.setAccountBalance(dbAccount.getAccountBalance() - amount);

        AccountUser user = dbAccount.getUser();
        mailService.withdrawalNotification(user.getUsername(), user.getFirstname(), amount);

        Transactions txn = new Transactions();
        txn.setAmount(amount);
        txn.setAccountNumber(accountNumber);
        txn.setTransactionId(transactionId);
        txn.setType(TransactionType.WITHDRAWAL);
        transactionsRepository.save(txn);
    }

    public BankAccount withdrawFund (String accountNumber, double amount) throws MessagingException {
        BankAccount dbAccount = bankAccountService.getAccountByAccountNumber(accountNumber);
        assert dbAccount != null;

        if (dbAccount.getAccountBalance() < amount) throw new TransactionException("Insufficient amount.");
        dbAccount.setAccountBalance(dbAccount.getAccountBalance() - amount);

        AccountUser user = dbAccount.getUser();
        mailService.withdrawalNotification(user.getUsername(), user.getFirstname(), amount);

        Transactions txn = new Transactions();
        txn.setAmount(amount);
        txn.setAccountNumber(accountNumber);
        txn.setTransactionId(generateTnxId());
        txn.setType(TransactionType.WITHDRAWAL);
        transactionsRepository.save(txn);

        return dbAccount;
    }

    @Transactional
    public void transferFund (String accountFrom, String accountTo, double amount) throws MessagingException {
        BankAccount dbAccountFrom = bankAccountService.getAccountByAccountNumber(accountFrom);
        BankAccount dbAccountTo = bankAccountService.getAccountByAccountNumber(accountTo);
        assert dbAccountFrom != null && dbAccountTo != null;
        String txnId = generateTnxId();
        withdrawFund(accountFrom, amount,txnId);
        depositFund(accountTo, amount, txnId);
    }

    private String generateTnxId () {
        String randomUUID = UUID.randomUUID().toString();
        return "TNX-" + randomUUID.substring(0,7);
    }
}
