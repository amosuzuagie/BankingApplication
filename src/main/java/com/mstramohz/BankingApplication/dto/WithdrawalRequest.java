package com.mstramohz.BankingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WithdrawalRequest {
    @NotEmpty(message = "Account number can not be empty.")
    private String accountNumber;
    @NotNull(message = "Amount can not be empty.")
    private Double amount;
}
