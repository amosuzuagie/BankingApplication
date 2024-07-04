package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.DepositRequest;
import com.mstramohz.BankingApplication.dto.Response;
import com.mstramohz.BankingApplication.dto.TransferRequest;
import com.mstramohz.BankingApplication.dto.WithdrawalRequest;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.entity.Transactions;
import com.mstramohz.BankingApplication.service.BankAccountService;
import com.mstramohz.BankingApplication.service.BankingOperationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    private final BankAccountService accountService;
    private final BankingOperationService operationService;

    @Autowired
    public BankAccountController (BankAccountService accountService, BankingOperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAccount () {
        List<BankAccount> accounts = accountService.getAllAccount();
        Map<String, Object> map = new HashMap<>();
        map.put("account", accounts);

        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountById (@PathVariable long id) {
        BankAccount account = accountService.getAccountById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<Response> getAccountBalance (@RequestParam String accountNumber) {
        Double balance = accountService.getAccountBalance(accountNumber);
        Response response = Response.build(true, "success", "Account balance", balance);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawFund (@RequestBody @Valid WithdrawalRequest request) throws MessagingException {
        Transactions txn = operationService.withdrawFund(request.getAccountNumber(), request.getAmount());

        Response response = Response.build(true, "Transaction successful.", "details", txn);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositFund (@RequestBody @Valid DepositRequest request) throws MessagingException {
        Transactions txn = operationService.depositFund(request.getAccountNumber(), request.getAmount());
        Response response = Response.build(true, "Transaction successful.", "details", txn);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFund (@RequestBody @Valid TransferRequest request) throws MessagingException {
        Map<String, Object> txn = operationService.transferFund(request.getAccountFrom(), request.getAccountTo(), request.getAmount());
        Response response = new Response(true, "Transaction successful", txn);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> openBankAccount (Double balance, String username) {
        BankAccount account = accountService.openBankAccount(balance, username);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        Response response = Response.build(true, "success", "Account", account);
        return ResponseEntity.ok(response);
    }
}
