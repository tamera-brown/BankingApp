package com.java.Bank.service;

import com.java.Bank.exceptions.InvalidTransactionIdException;
import com.java.Bank.exceptions.InvalidTransactionTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransactionById(String id) throws InvalidTransactionIdException;

    List<Transaction> getTransactionByType(String type) throws InvalidTransactionTypeException;

    Transaction deposit(Double depositAmount) throws InvalidUserIdException;

    List<Transaction> getTransactionsByUserId(String userId) throws InvalidUserIdException;

    Transaction withdraw(Double withdrawAmount) throws InvalidUserIdException;
}
