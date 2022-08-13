package com.java.Bank.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    @Indexed(unique = true)
    private String email;
    private String phoneNum;
    private String password;
    @DBRef
    private List<Account> account;


    public User(){}

    public User(String firstName, String lastName, String address, String email, String phoneNum, String password, List<Account> account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.account = account;

    }
}
