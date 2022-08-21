package com.java.Bank.payload;

import lombok.Data;

@Data
public class AuthenticationResponse {
    String accessToken;
    String tokenType= "Bearer";
}
