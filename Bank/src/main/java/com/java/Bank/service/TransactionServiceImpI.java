package com.java.Bank.service;

import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import com.java.Bank.requests.DepositRequest;
import com.java.Bank.requests.TransferRequest;
import com.java.Bank.requests.WithdrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        } catch (Exception e) {
            throw new InvalidTransactionTypeException("Transaction with that type does not exist");
        }

    }

    @Override
    public Transaction deposit(DepositRequest depositRequest) throws MissingPropertyException, PositiveAmountException {
        if (depositRequest.getDepositAmount() < 0) {
            throw new PositiveAmountException("Deposit amount must be positive");
        }

        Transaction transaction = new Transaction();

        Optional<User> user = userRepo.findById(depositRequest.getUserId());

        Optional<Account> account = accountRepo.findById(depositRequest.getAccountNum());
        Stack<Transaction> allTransactions = account.get().getTransaction();
        if (user.isPresent()) {
            account.get().setBalance(account.get().getBalance() + depositRequest.getDepositAmount());
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setDescription("deposit of $" + String.format("%.2f", depositRequest.getDepositAmount()));
            transactionRepo.insert(transaction);
            allTransactions.push(transaction);
            account.get().setTransaction(allTransactions);
            accountRepo.save(account.get());

        } else
            throw new MissingPropertyException("user does not exist");

        return transaction;
    }


    @Override
    public List<Transaction> getTransactionsByaccountNum(String accountNum) throws InvalidAccountIdException {

        try {
            Stack<Transaction> transactions = accountRepo.findById(accountNum).get().getTransaction();
            return transactions;
        } catch (Exception e) {
            throw new InvalidAccountIdException("Account with that type does not exist");
        }

    }

    @Override
    public Transaction withdraw(WithdrawRequest withdrawRequest) throws MissingPropertyException, PositiveAmountException, InsufficientFundsException {
        if (withdrawRequest.getWithdrawAmount() < 0) {
            throw new PositiveAmountException("Withdraw amount must be positive");
        }

        Transaction transaction = new Transaction();

        Optional<User> user = userRepo.findById(withdrawRequest.getUserId());

        Optional<Account> account = accountRepo.findById(withdrawRequest.getAccountNum());
        Stack<Transaction> allTransactions = account.get().getTransaction();
        if (account.get().getBalance() < withdrawRequest.getWithdrawAmount()) {
            throw new InsufficientFundsException("Cannot withdraw due to insufficient funds");
        }
        if (user.isPresent()) {
            account.get().setBalance(account.get().getBalance() - withdrawRequest.getWithdrawAmount());
            accountRepo.save(account.get());
            transaction.setTransactionType(TransactionType.WITHDRAW);
            transaction.setDescription("withdrawn $" + String.format("%.2f", withdrawRequest.getWithdrawAmount()));
            transactionRepo.insert(transaction);
            allTransactions.push(transaction);
            account.get().setTransaction(allTransactions);
            accountRepo.save(account.get());
        } else
            throw new MissingPropertyException("user does not exist");

        return transaction;
    }

    @Override
    public Stack<Transaction> transfer(TransferRequest transferRequest) throws PositiveAmountException, MissingPropertyException, InsufficientFundsException, DuplicateAccountException {
        Stack<Transaction>transactionStack=new Stack<>();
        if (transferRequest.getGiveAccNum().equals(transferRequest.getReceiveAccNum())) {
            throw new DuplicateAccountException("Cannot transfer to same account");
        }
        DepositRequest newDepositRequest = new DepositRequest();
        WithdrawRequest newWithdrawRequest = new WithdrawRequest();

        newWithdrawRequest.setUserId(transferRequest.getGiveUserId());
        newWithdrawRequest.setAccountNum(transferRequest.getGiveAccNum());
        newWithdrawRequest.setWithdrawAmount(transferRequest.getTransferAmount());
        transactionStack.push(withdraw(newWithdrawRequest));
        newDepositRequest.setUserId(transferRequest.getReceiveUserId());
        newDepositRequest.setAccountNum(transferRequest.getReceiveAccNum());
        newDepositRequest.setDepositAmount(transferRequest.getTransferAmount());
        transactionStack.push(deposit(newDepositRequest));

        return transactionStack;

    }
}
