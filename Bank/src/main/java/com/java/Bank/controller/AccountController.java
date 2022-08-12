package com.java.Bank.controller;

import com.java.Bank.exceptions.InvalidAccountIdException;
import com.java.Bank.exceptions.InvalidAccountTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.MissingPropertyException;
import com.java.Bank.model.Account;
import com.java.Bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    AccountService service;

    @PostMapping("/AddAccount/{userId}")
    public ResponseEntity addAccount(@RequestBody Account newAccount, @PathVariable String userId) {
        try {
            return new ResponseEntity(service.addAccount(newAccount,userId), HttpStatus.CREATED);
        }catch (MissingPropertyException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity getAllAccounts(){
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getAccountsByUserId(@PathVariable String userId){
        try{
            return ResponseEntity.ok(service.getAccountsByUserId(userId));
        }catch (InvalidUserIdException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{type}")
    public ResponseEntity getAccountByAccountType(@PathVariable String type){
        try{
            return ResponseEntity.ok(service.getAccountsByAccountType(type));
        }catch (InvalidAccountTypeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity deleteAccountById(@PathVariable String id){
        try {
            service.deleteAccountById(id);
            return new ResponseEntity("Account successfully deleted", HttpStatus.NO_CONTENT);
        }catch(InvalidAccountIdException ex){
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
    }
}