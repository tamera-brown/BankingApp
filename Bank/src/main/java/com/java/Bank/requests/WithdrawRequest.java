package com.java.Bank.requests;

import lombok.Data;

@Data
public class WithdrawRequest {
    private String userId;
    private String accountNum;
    private Double withdrawAmount;
}
