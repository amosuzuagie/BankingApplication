package com.mstramohz.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(name = "accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String accountNumber;

    @Setter
    private Double accountBalance;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser user;

    public BankAccount () {}

    public BankAccount (String accountNumber, Double accountBalance, AccountUser user) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.user = user;
    }
}
