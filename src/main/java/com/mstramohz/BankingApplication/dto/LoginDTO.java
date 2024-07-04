package com.mstramohz.BankingApplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "Invalid email format.")
    @NotEmpty(message = "Username can not be empty.")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")
    @NotEmpty(message = "Password can not be empty.")
    private String password;
}
