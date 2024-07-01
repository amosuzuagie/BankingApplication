package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.config.JwtService;
import com.mstramohz.BankingApplication.dto.LoginDTO;
import com.mstramohz.BankingApplication.dto.LoginRequest;
import com.mstramohz.BankingApplication.dto.LoginResponse;
import com.mstramohz.BankingApplication.dto.UserInfo;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.entity.Token;
import com.mstramohz.BankingApplication.enums.Role;
import com.mstramohz.BankingApplication.repository.AccountUserRepository;
import com.mstramohz.BankingApplication.repository.TokenRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final MailService mailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final BankAccountService accountService;
    private final AccountUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService(
            JwtService jwtService,
            MailService mailService,
            TokenService tokenService,
            TokenRepository tokenRepository,
            BankAccountService accountService,
            AccountUserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AccountUser> createUser (UserInfo userInfo) throws MessagingException {
        AccountUser user = new AccountUser();

        user.setFirstname(userInfo.getFirstname());
        user.setLastname(userInfo.getLastname());
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setRole(Role.USER);
        AccountUser savedUser = userRepository.save(user);

        BankAccount account = accountService.openBankAccount(savedUser);

        //generate and send token via mail
        String tokenValue = tokenService.generateToken();
        tokenService.saveToken(savedUser, tokenValue);
        mailService.registrationNotification(savedUser, tokenValue, account.getAccountNumber());

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public String login (LoginDTO dto) {
        //Verifying email validity
        if (userRepository.findByUsername(dto.getUsername()).isEmpty()) throw new UsernameNotFoundException("Invalid email.");

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        if (auth != null) {
            return jwtService.generateToken((AccountUser) auth.getPrincipal());
        }else {
            throw new ArithmeticException("Authentication denied.");
        }
    }

    /*public void logout (String authToken) {
        jwtService.invalidateToken(authToken);
    }*/

    public void AccountUserVerification (String value) throws Exception {
        if (!value.isEmpty()) {
            Optional<Token> token = tokenRepository.findByValue(value);
            if (token.isEmpty()) throw new Exception("Invalid token.");
            Token dbToken = token.get();

            if (dbToken.getExpiresAt().isBefore(LocalDateTime.now())) throw new Exception("Expired token. Request for new token");

            AccountUser user = dbToken.getUser();
            if (user == null) {
                log.error("User not found for token with id: {}", dbToken.getId());
                throw new Exception("Internal error. Kindly contact for support.");
            }
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            throw new Exception("Token is required.");
        }
    }
}
