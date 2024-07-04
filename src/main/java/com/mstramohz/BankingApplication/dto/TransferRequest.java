package com.mstramohz.BankingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {
    @NotEmpty(message = "Account can not be empty.")
    private String accountFrom;
    @NotEmpty(message = "Account can not be empty.")
    private String accountTo;
    @NotNull(message = "amount can not  be empty.")
    private Double amount;
}
