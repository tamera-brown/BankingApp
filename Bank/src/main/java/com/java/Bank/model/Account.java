package com.java.Bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.Bank.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private String accountNum;
    private AccountType accountType;
    private double balance;
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm:ss a")
    private LocalDateTime accountopened;

    public Account(){

    }

    public Account(AccountType accountType, double balance, LocalDateTime accountopened) {
        this.accountType = accountType;
        this.balance = balance;
        this.accountopened = accountopened;
    }
}
