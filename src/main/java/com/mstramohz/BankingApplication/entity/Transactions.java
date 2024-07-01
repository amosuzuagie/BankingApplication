package com.mstramohz.BankingApplication.entity;

import com.mstramohz.BankingApplication.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Entity(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @NotEmpty
    private String accountNumber;

    @Column(updatable = false)
    private LocalDateTime dateTime;

    @Setter
    private Double amount;

    @Setter
    private String transactionId;

    @Setter
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Transactions () {}

    @PrePersist
    public void setDateTime () {
        this.dateTime = LocalDateTime.now();
    }

}
