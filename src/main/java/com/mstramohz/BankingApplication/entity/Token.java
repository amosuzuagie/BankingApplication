package com.mstramohz.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "token")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String value;

    @ManyToOne(optional = false)
    private AccountUser user;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Token () {}

    @PrePersist
    public void setCreatedAt () {
        this.createdAt = LocalDateTime.now();
    }
}
