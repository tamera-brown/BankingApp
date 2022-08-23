package com.java.Bank.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    @Pattern(regexp ="^(?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!*]).{8,30}")
    private String password;

}
