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
    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private String bvn;

    @Setter
    @Column(nullable = false)
    private String nin;

    @Setter
    @Column(nullable = false)
    private String residentLga;

    @Setter
    @Column(nullable = false)
    private String residentState;

    @Setter
    @Column(nullable = false)
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
