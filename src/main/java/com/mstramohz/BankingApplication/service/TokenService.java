package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.Token;
import com.mstramohz.BankingApplication.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Autowired
    private final TokenRepository repository;

    protected Token saveToken(AccountUser user, String value) {
        Token token = new Token();
        token.setValue(value);
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15L));
        return repository.save(token);
    }

    protected String  generateToken () {
        String value = UUID.randomUUID().toString();
        if (repository.findByValue(value).isPresent()) generateToken();
        return value;
    }
}
