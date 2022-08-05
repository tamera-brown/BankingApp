package com.java.Bank.service;

import com.java.Bank.exceptions.InvalidTransactionIdException;
import com.java.Bank.exceptions.InvalidTransactionTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpI implements TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction getTransactionById(String id) throws InvalidTransactionIdException {
        Transaction retrieved = null;
        Optional<Transaction> transaction = transactionRepo.findById(id);
        if (transaction.isPresent()) {
            retrieved = transaction.get();
            return retrieved;
        } else {
            throw new InvalidTransactionIdException("Transaction with that id does not exist");
        }
    }

    @Override
    public List<Transaction> getTransactionByType(String type) throws InvalidTransactionTypeException {
        try {
            List<Transaction> transactions = transactionRepo.findTransactionByType(type.toUpperCase());
            return transactions;
        } catch (Exception e){
            throw new InvalidTransactionTypeException("Transaction with that type does not exist");
        }

    }

    @Override
    public Transaction deposit(Double depositAmount) throws InvalidUserIdException {
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userId) throws InvalidUserIdException {
        User retrieved = null;
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            retrieved = user.get();
            return retrieved.getTransaction();
        }
        else{
            throw new InvalidUserIdException("User with that id does not exist");
        }
    }


    @Override
    public Transaction withdraw(Double withdrawAmount) throws InvalidUserIdException {
        return null;
    }
}
