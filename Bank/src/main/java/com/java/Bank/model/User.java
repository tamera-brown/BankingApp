package com.java.Bank.model;


import com.java.Bank.Authority;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
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
    private String username;
    @Indexed(unique = true)
    private String email;
    private String phoneNum;
    private String password;
    @DBRef
    private List<Account> account;
    private List<Authority> authorities=new ArrayList<>();


    public User() {
    }

    public User(String firstName, String lastName, String address, String username, String email, String phoneNum, String password, List<Account> account,List<Authority> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.username = username;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.account = account;
        this.authorities=authorities;

    }


}