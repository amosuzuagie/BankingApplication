package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.Response;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    private final BankAccountService accountService;

    public BankAccountController (BankAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccount () {
        List<BankAccount> accounts = accountService.getAllAccount();

        Map<String, Object> map = new HashMap<>();
        map.put("account", accounts);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById (@PathVariable long id) {
        BankAccount account = accountService.getAccountById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> openBankAccount (double balance, AccountUser user) {
        BankAccount account = accountService.openBankAccount(balance, user);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> openBankAccount ( AccountUser user) {
        BankAccount account = accountService.openBankAccount(user);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }


}
