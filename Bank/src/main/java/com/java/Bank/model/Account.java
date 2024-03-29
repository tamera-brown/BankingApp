package com.java.Bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.Bank.AccountStatus;
import com.java.Bank.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Stack;

@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private String accountNum;
    private AccountType accountType;
    private Double balance;
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm:ss a")
    private LocalDateTime accountopened =LocalDateTime.now(ZoneId.systemDefault());
    @DBRef
    private Stack<Transaction> transaction;
    private AccountStatus accountStatus;

    public Account(){

    }

    public Account(AccountType accountType, Double balance, LocalDateTime accountopened,Stack<Transaction> transaction,AccountStatus accountStatus) {
        this.accountType = accountType;
        this.balance = balance;
        this.accountopened = accountopened;
        this.transaction=transaction;
        this.accountStatus=accountStatus;
    }
}
