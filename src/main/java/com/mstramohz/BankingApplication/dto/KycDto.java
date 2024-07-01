package com.mstramohz.BankingApplication.dto;

import com.mstramohz.BankingApplication.entity.AccountUser;
import lombok.Data;

@Data
public class KycDto {

    private String address;

    private String bvn;

    private String nin;

    private String residentLga;

    private String residentState;

    private String dob;

    private String nextOfKin;

    private AccountUser user;

}
