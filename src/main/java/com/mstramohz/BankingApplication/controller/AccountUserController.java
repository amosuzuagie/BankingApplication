package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.UserInfo;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.service.AccountUserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountUserController {
    private final AccountUserService userService;

    @Autowired
    public AccountUserController (AccountUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountUser>> getAllAccountUsers () {
        return userService.getAllAccountUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AccountUser> getUserById (@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountUser> getUserByUsername (@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<AccountUser> createUser (@RequestBody @Valid  UserInfo userInfo) throws MessagingException {
        return userService.createUser(userInfo);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountUser> updateUser (@PathVariable long id, @RequestBody @Valid AccountUser update) {
        return userService.updateUser(id, update);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AccountUser> deleteUser (@PathVariable long id) {
        return userService.deleteUser(id);
    }
}
