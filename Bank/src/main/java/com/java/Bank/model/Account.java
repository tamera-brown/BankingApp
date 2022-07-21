package com.java.Bank.model;

import com.java.Bank.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "accounts")
public class Account {
    @Id
    private String accountNum;
    private AccountType accountType;
    private double balance;

    public Account(){

    }

    public Account(String accountNum, AccountType accountType, double balance) {
        this.accountNum = accountNum;
        this.accountType = accountType;
        this.balance = balance;
    }
}
