package com.java.Bank.service;

import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.InvalidTransactionIdException;
import com.java.Bank.exceptions.InvalidTransactionTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.MissingPropertyException;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import com.java.Bank.requests.DepositRequest;
import com.java.Bank.requests.WithdrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class TransactionServiceImpI implements TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    AccountRepo accountRepo;
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
    public Transaction deposit(DepositRequest depositRequest) throws MissingPropertyException {
        Transaction transaction=new Transaction();

        Optional<User> user = userRepo.findById(depositRequest.getUserId());

        Optional<Account> account = accountRepo.findById(depositRequest.getAccountNum());


        if (user.isPresent()){

            account.get().setBalance(account.get().getBalance()+depositRequest.getDepositAmount());
            accountRepo.save(account.get());
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setDescription("deposit of $" + String.format("%.2f",depositRequest.getDepositAmount()));
            transaction.setTransactionDate(LocalDateTime.now());
            transactionRepo.insert(transaction);
            user.get().getTransaction().push(transaction);
            userRepo.save(user.get());
        } else
            throw new MissingPropertyException("user does not exist");

        return transaction;
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
    public Transaction withdraw(WithdrawRequest withdrawRequest) throws MissingPropertyException {
        Transaction transaction=new Transaction();

        Optional<User> user = userRepo.findById(withdrawRequest.getUserId());

        Optional<Account> account = accountRepo.findById(withdrawRequest.getAccountNum());
        if (user.isPresent()){
            account.get().setBalance(account.get().getBalance()-withdrawRequest.getWithdrawAmount());
            accountRepo.save(account.get());
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setDescription("withdraw of $" + String.format("%.2f",withdrawRequest.getWithdrawAmount()));
            transaction.setTransactionDate(LocalDateTime.now());
            transactionRepo.insert(transaction);
            user.get().getTransaction().push(transaction);
            userRepo.save(user.get());
        } else
            throw new MissingPropertyException("user does not exist");

        return transaction;
    }

}
