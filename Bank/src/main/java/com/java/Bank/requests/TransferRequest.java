package com.java.Bank.requests;

import lombok.Data;

@Data
public class TransferRequest {
    private String giveUserId;
    private String giveAccNum;
    private String receiveUserId;
    private String receiveAccNum;
    private Double transferAmount;

}
