package com.java.Bank.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;
import java.util.Stack;

@Data
@Document(collation = "users")
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    @Indexed(unique = true)
    private String email;
    @NumberFormat
    private String phoneNum;
    private String password;
    @DBRef
    private List<Account> account;
    @DBRef
    private Stack<Transaction> transaction;

    public User(){}

    public User(String userId, String firstName, String lastName, String address, String email, String phoneNum, String password, List<Account> account, Stack<Transaction> transaction) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.account = account;
        this.transaction = transaction;
    }
}
