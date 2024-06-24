package com.mstramohz.BankingApplication.repository;

import com.mstramohz.BankingApplication.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByValue(String token);
}
