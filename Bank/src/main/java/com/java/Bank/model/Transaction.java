package com.java.Bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.Bank.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String transactionId;
    private TransactionType transactionType;;
    private Double transactionAmount;
    private String description;
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm:ss a")
    private LocalDateTime transactionDate=LocalDateTime.now(ZoneId.systemDefault());


    public Transaction(){

    }

    public Transaction(TransactionType transactionType, Double transactionAmount, String description, LocalDateTime transactionDate) {
        this.transactionType = transactionType;
        this.transactionAmount=transactionAmount;
        this.description = description;
        this.transactionDate = transactionDate;

    }
}
