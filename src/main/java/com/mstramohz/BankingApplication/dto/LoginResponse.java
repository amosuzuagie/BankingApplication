package com.mstramohz.BankingApplication.dto;

import com.mstramohz.BankingApplication.entity.AccountUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private AccountUser user;
    private String token;
}
