package com.java.Bank.requests;

import lombok.Data;

@Data
public class WithdrawRequest {
    private String username;
    private String accountNum;
    private Double withdrawAmount;
}
