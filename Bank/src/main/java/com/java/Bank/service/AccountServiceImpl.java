package com.java.Bank.service;

import com.java.Bank.AccountType;
import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Stack;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public Account addAccount(Account account, String username) throws MissingPropertyException {
            User user = userRepo.findUserByUsername(username);
        Transaction newTransaction = new Transaction();
        Stack<Transaction>allTransactions=new Stack<>();
            if (user!=null){
                account.setAccountType(account.getAccountType());
                account.setBalance(account.getBalance());
                user.getAccount().add(account);
                newTransaction.setTransactionType(TransactionType.DEPOSIT);
                newTransaction.setDescription("initial deposit of $" + String.format("%.2f",user.getAccount().get(user.getAccount().size()-1).getBalance()));
                allTransactions.push(newTransaction);
                account.setTransaction(allTransactions);
                transactionRepo.insert(newTransaction);
                accountRepo.insert(account);
                userRepo.save(user);
            } else
                throw new MissingPropertyException("user does not exist");

            return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public List<Account> getAccountsByUsername(String username) throws InvalidUsernameException {
        try{
            User user= userRepo.findUserByUsername(username);
            return user.getAccount();
        } catch (Exception e){
            throw new InvalidUsernameException("User with that username does not exist");
        }
    }

    @Override
    public List<Account> getAccountsByAccountType(String type) throws InvalidAccountTypeException {
        AccountType accountType = AccountType.valueOf(type.toUpperCase());

        if (accountType.name().equalsIgnoreCase(type)) {
            accountRepo.findAccountsByType(accountType);
        } else {
            throw new InvalidAccountTypeException("invalid account type");
        }
        return accountRepo.findAccountsByType(accountType);

    }

    @Override
    public void deleteAccountById(String id) throws InvalidAccountIdException {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            throw new InvalidAccountIdException("User with that id does not exist");
        }
    }
}
