package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.Response;
import com.mstramohz.BankingApplication.dto.UserInfo;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.service.AccountUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/id/{id}")
    public ResponseEntity<AccountUser> getUserById (@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email")
    public ResponseEntity<AccountUser> getUserByUsername (@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser (@RequestBody @Valid UserInfo userInfo) throws Exception {
        userService.createUser(userInfo);

        Response response = new Response(true, "Signed up. mailbox to verify your account.", null);
        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController.class).authenticate(null)).withRel("login");
        response.add(loginLink);
//        return ResponseEntity.status(201).body(Optional.of(response));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountUser> updateUser (@PathVariable long id, @RequestBody @Valid AccountUser update) {
        return userService.updateUser(id, update);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AccountUser> deleteUser (@PathVariable long id) {
        return userService.deleteUser(id);
    }
}
