package com.java.Bank.controller;

import com.java.Bank.exceptions.*;
import com.java.Bank.requests.DepositRequest;
import com.java.Bank.requests.WithdrawRequest;
import com.java.Bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    TransactionService service;

    @GetMapping()
    public ResponseEntity getAllTransactions(){
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTransactionById(@PathVariable String id){
        try {
            return  ResponseEntity.ok(service.getTransactionById(id));
        }catch(InvalidTransactionIdException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/types/{type}")
    public ResponseEntity getTransactionByType(@PathVariable String type){
        try {
            return  ResponseEntity.ok(service.getTransactionByType(type));
        }catch(InvalidTransactionTypeException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity getTransactionsByUserId(@PathVariable String userId){
        try{
            return ResponseEntity.ok(service.getTransactionsByUserId(userId));
        }catch (InvalidUserIdException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody DepositRequest depositRequest) {
        try {
            return ResponseEntity.ok(service.deposit(depositRequest));
        } catch (MissingPropertyException | PositiveAmountException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody WithdrawRequest withdrawRequest) {
        try {
            return ResponseEntity.ok(service.withdraw(withdrawRequest));
        } catch (MissingPropertyException | PositiveAmountException | InsufficientFundsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}