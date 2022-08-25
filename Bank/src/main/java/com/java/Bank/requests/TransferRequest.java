package com.java.Bank.requests;

import lombok.Data;

@Data
public class TransferRequest {
    private String giveUsername;
    private String giveAccNum;
    private String receiveUsername;
    private String receiveAccNum;
    private Double transferAmount;

}
