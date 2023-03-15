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

    @GetMapping("/{id}")
    public ResponseEntity getAccountById(@PathVariable String id){
        try {
            return  ResponseEntity.ok(service.getAccountById(id));
        }catch(InvalidAccountIdException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
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
    @GetMapping("/type/{type}")
    public ResponseEntity getAccountByAccountType(@PathVariable String type){
        try{
            return ResponseEntity.ok(service.getAccountsByAccountType(type));
        }catch (InvalidAccountTypeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/status/{status}")
    public ResponseEntity getAccountByAccountStatus(@PathVariable String status){
        try{
            return ResponseEntity.ok(service.getAccountsByAccountStatus(status));
        }catch (InvalidAccountStatusException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/closeAccount/{id}")
    public ResponseEntity closeAccountById(@PathVariable String id){
        try {
            service.closeAccountById(id);
            return new ResponseEntity("Account successfully closed", HttpStatus.NO_CONTENT);
        }catch(InvalidAccountIdException ex){
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
    }
}
