package com.mstramohz.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity(name = "kyc")
public class KnowYourCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String address;

    @Setter
    private String bvn;

    @Setter
    private String nin;

    @Setter
    private String residentLga;

    @Setter
    private String residentState;

    @Setter
    private String dob;

    @Setter
    private String nextOfKin;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AccountUser user;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public KnowYourCustomer () {}

    public KnowYourCustomer(String address, String bvn, String nin, String residentLga, String residentState, String dob, String nextOfKin, AccountUser user) {
        this.address = address;
        this.bvn = bvn;
        this.nin = nin;
        this.residentLga = residentLga;
        this.residentState = residentState;
        this.dob = dob;
        this.nextOfKin = nextOfKin;
        this.user = user;
    }

    @PrePersist
    public void onCreate () {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate () {
        this.updatedAt = LocalDateTime.now();
    }
}
