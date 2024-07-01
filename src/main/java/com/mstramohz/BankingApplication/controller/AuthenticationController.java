package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.*;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController (AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountUser> createUser (@RequestBody @Valid UserInfo userInfo) throws MessagingException {
        return authenticationService.createUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> authenticate (@RequestBody LoginDTO dto) throws Exception {
        String auth = authenticationService.login(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("auth", auth);
        Response response = new Response(true, "Login successful", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login"; // or wherever you want to redirect after logout
    }

    @GetMapping("/email_verification/{value}")
    public void AccountUserVerification (@PathVariable String value) throws Exception {
        authenticationService.AccountUserVerification(value);
    }
}
