package com.java.Bank.requests;


import lombok.Data;

@Data
public class DepositRequest {
    private String username;
    private String accountNum;
    private Double depositAmount;
}
