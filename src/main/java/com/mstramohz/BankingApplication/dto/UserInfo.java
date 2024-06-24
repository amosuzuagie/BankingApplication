package com.mstramohz.BankingApplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class UserInfo {
    @NotEmpty(message = "First name is required.")
    @Length(min = 3, message = "First name can not be less than 3 letters.")
    private String firstname;

    @NotEmpty(message = "Last name is required.")
    @Length(min = 3, message = "Last name can not be less than 3 letters.")
    private String lastname;

    @Email(message = "Please put a valid email.")
    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "[0-9]{11}")
    private String phoneNumber;
}
