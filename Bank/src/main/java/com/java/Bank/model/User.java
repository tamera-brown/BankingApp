package com.java.Bank.model;


import com.java.Bank.Authority;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^\\d+ [A-Z][a-z]* (Street|Avenue) [A-Z][a-z]*, [A-Z]{2} \\d{5}$")
    private String address;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    @Email
    private String email;
    @Pattern(regexp = "^\\(\\d{3}\\)\\s?\\d{3}-\\d{4}$")
    private String phoneNum;
    @Pattern(regexp ="^(?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!*]).{8,15}")
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