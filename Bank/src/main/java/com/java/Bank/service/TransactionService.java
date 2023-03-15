package com.java.Bank.service;

import com.java.Bank.exceptions.*;
import com.java.Bank.model.Transaction;
import com.java.Bank.requests.DepositRequest;
import com.java.Bank.requests.TransferRequest;
import com.java.Bank.requests.WithdrawRequest;

import java.awt.datatransfer.MimeTypeParseException;
import java.util.List;
import java.util.Stack;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransactionById(String id) throws InvalidTransactionIdException;

    List<Transaction> getTransactionByType(String type) throws InvalidTransactionTypeException;

    Transaction deposit(DepositRequest depositRequest) throws PositiveAmountException, DuplicateAccountException, InvalidUsernameException, InvalidAccountNumException;

    Transaction withdraw(WithdrawRequest withdrawRequest) throws PositiveAmountException, InsufficientFundsException, DuplicateAccountException, InvalidAccountNumException, InvalidUsernameException;

    Stack<Transaction> transfer(TransferRequest transferRequest) throws PositiveAmountException, MissingPropertyException, InsufficientFundsException, DuplicateAccountException, InvalidUserIdException, InvalidUsernameException, InvalidAccountNumException;
}
