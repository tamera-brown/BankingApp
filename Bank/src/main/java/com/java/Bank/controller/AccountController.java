package com.java.Bank.controller;

import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;
import com.java.Bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @Autowired
    AccountService service;

    @PostMapping("/AddAccount/{username}")
    public ResponseEntity addAccount(@RequestBody @Valid Account newAccount, @PathVariable String username) {
        try {
            return new ResponseEntity(service.addAccount(newAccount,username), HttpStatus.CREATED);
        }catch (MissingPropertyException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity getAllAccounts(){
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity getAccountsByUsername(@PathVariable String username){
        try{
            return ResponseEntity.ok(service.getAccountsByUsername(username));
        }catch (InvalidUsernameException e){
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
