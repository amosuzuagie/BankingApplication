package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.*;
import com.mstramohz.BankingApplication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController (AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser (@RequestBody @Valid UserInfo userInfo) throws Exception {
        authenticationService.createUser(userInfo);

        Response response = new Response(true, "Signed up. Check your mail to verify your account.", null);

        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).authenticate(null)).withRel("login");
        Link mailVerification = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController.class).AccountUserVerification(null)).withRel("verification");
        response.add(loginLink, mailVerification);
        return ResponseEntity.status(201).body(Optional.of(response));
    }

    @PostMapping("/register_admin_user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdminUser (@RequestBody @Valid UserInfo userInfo) throws Exception {
        authenticationService.createAdminUser(userInfo);

        Response response = new Response(true, "Signed up. Check your mail to verify your account.", null);

        Link loginLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).authenticate(null)).withRel("login");
        Link mailVerification = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController.class).AccountUserVerification(null)).withRel("verification");
        response.add(loginLink, mailVerification);
        return ResponseEntity.status(201).body(Optional.of(response));
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
    public ResponseEntity<Response> AccountUserVerification (@PathVariable String value) throws Exception {
        authenticationService.AccountUserVerification(value);
        Response response = new Response(true, "Verified", null);
        return ResponseEntity.ok(response);
    }
}
