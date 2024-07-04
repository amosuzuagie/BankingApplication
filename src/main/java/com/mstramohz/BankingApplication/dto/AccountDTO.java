package com.mstramohz.BankingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountDTO {
    @NotEmpty(message = "Account number ca not be empty.")
    private String accountNumber;

    @NotEmpty(message = "Account balance can not be empty.")
    private Double accountBalance;
}
