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
        try {
            User user = userRepo.findUserByUsername(depositRequest.getUsername());
            for (Account account : user.getAccount()) {
                if (account.getAccountNum().equals(depositRequest.getAccountNum())) {
                    Stack<Transaction> allTransactions = account.getTransaction();
                    account.setBalance(account.getBalance() + depositRequest.getDepositAmount());
                    accountRepo.save(account);
                    transaction.setTransactionType(TransactionType.DEPOSIT);
                    transaction.setDescription("deposited $" + String.format("%.2f", depositRequest.getDepositAmount()));
                    transactionRepo.insert(transaction);
                    allTransactions.push(transaction);
                    account.setTransaction(allTransactions);
                    accountRepo.save(account);

                }
            }
        }catch (Exception e){
            throw new MissingPropertyException("user does not exist");
        }
        return transaction;
    }

    @Override
    public Transaction withdraw(WithdrawRequest withdrawRequest) throws MissingPropertyException, PositiveAmountException, InsufficientFundsException {
        if (withdrawRequest.getWithdrawAmount() < 0) {
            throw new PositiveAmountException("Withdraw amount must be positive");
        }

        Transaction transaction = new Transaction();
        try{

            User user = userRepo.findUserByUsername(withdrawRequest.getUsername());
            for (Account account : user.getAccount()) {
                if (account.getAccountNum().equals(withdrawRequest.getAccountNum())) {
                    Stack<Transaction> allTransactions = account.getTransaction();
                    if (account.getBalance() < withdrawRequest.getWithdrawAmount()) {
                        throw new InsufficientFundsException("Cannot withdraw due to insufficient funds");
                    }
                    account.setBalance(account.getBalance() - withdrawRequest.getWithdrawAmount());
                    accountRepo.save(account);
                    transaction.setTransactionType(TransactionType.WITHDRAW);
                    transaction.setDescription("withdrawn $" + String.format("%.2f", withdrawRequest.getWithdrawAmount()));
                    transactionRepo.insert(transaction);
                    allTransactions.push(transaction);
                    account.setTransaction(allTransactions);
                    accountRepo.save(account);
                }
            }
        }catch (Exception e){
            throw new MissingPropertyException("user does not exist");
        }

        return transaction;
    }

    @Override
    public Stack<Transaction> transfer(TransferRequest transferRequest) throws PositiveAmountException, MissingPropertyException, InsufficientFundsException, DuplicateAccountException, InvalidUserIdException {
        Stack<Transaction> transactionStack = new Stack<>();
        if (transferRequest.getGiveAccNum().equals(transferRequest.getReceiveAccNum())) {
            throw new DuplicateAccountException("Cannot transfer to same account");
        }
        DepositRequest newDepositRequest = new DepositRequest();
        WithdrawRequest newWithdrawRequest = new WithdrawRequest();

        newWithdrawRequest.setUsername(transferRequest.getGiveUsername());
        newWithdrawRequest.setAccountNum(transferRequest.getGiveAccNum());
        newWithdrawRequest.setWithdrawAmount(transferRequest.getTransferAmount());
        transactionStack.push(withdraw(newWithdrawRequest));
        newDepositRequest.setUsername(transferRequest.getReceiveUsername());
        newDepositRequest.setAccountNum(transferRequest.getReceiveAccNum());
        newDepositRequest.setDepositAmount(transferRequest.getTransferAmount());
        transactionStack.push(deposit(newDepositRequest));

        return transactionStack;

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



}
