package com.java.Bank.requests;

import lombok.Data;

@Data
public class TransferRequest {
    private String username;
    private String giveAccNum;
    private String receiveAccNum;
    private Double transferAmount;

}
