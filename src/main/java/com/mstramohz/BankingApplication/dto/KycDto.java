package com.mstramohz.BankingApplication.dto;

import com.mstramohz.BankingApplication.entity.AccountUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KycDto {

    @NotEmpty(message = "Address can not be empty.")
    private String address;

    @NotEmpty(message = "BVN can not be empty.")
    @Pattern(regexp = "[0-9]{11}")
    private String bvn;

    @NotEmpty(message = "NIN can not be empty.")
    @Pattern(regexp = "[0-9]{11}")
    private String nin;

    @NotEmpty(message = "LGA can not be empty.")
    private String residentLga;

    @NotEmpty(message = "State can not be empty.")
    private String residentState;

    @NotEmpty(message = "Date of birth can not be empty.")
    private String dob;

    private String nextOfKin;

    @NotEmpty(message = "Bank user can not be empty.")
    private AccountUser user;

}
