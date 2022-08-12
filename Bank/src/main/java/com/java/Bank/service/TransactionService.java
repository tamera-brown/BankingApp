package com.java.Bank.service;

import com.java.Bank.exceptions.*;
import com.java.Bank.model.Transaction;
import com.java.Bank.requests.DepositRequest;
import com.java.Bank.requests.WithdrawRequest;

import java.awt.datatransfer.MimeTypeParseException;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransactionById(String id) throws InvalidTransactionIdException;

    List<Transaction> getTransactionByType(String type) throws InvalidTransactionTypeException;

    Transaction deposit(DepositRequest depositRequest) throws MissingPropertyException, PositiveAmountException;

    List<Transaction> getTransactionsByUserId(String userId) throws InvalidUserIdException;

    Transaction withdraw(WithdrawRequest withdrawRequest) throws MissingPropertyException, PositiveAmountException, InsufficientFundsException;
}
