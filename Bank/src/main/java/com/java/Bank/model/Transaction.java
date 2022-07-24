package com.java.Bank.model;

import com.java.Bank.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String transactionId;
    private TransactionType transactionType;;
    private String description;
    private LocalDateTime transactionDate;

    public Transaction(){

    }

    public Transaction(TransactionType transactionType, String description, LocalDateTime transactionDate) {
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;
    }
}
