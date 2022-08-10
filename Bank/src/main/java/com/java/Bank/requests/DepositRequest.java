package com.java.Bank.requests;


import lombok.Data;

@Data
public class DepositRequest {
    private String userId;
    private String accountNum;
    private Double depositAmount;
}
