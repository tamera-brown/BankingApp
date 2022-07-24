package com.java.Bank.service;

import com.java.Bank.AccountType;
import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.InvalidAccountIdException;
import com.java.Bank.exceptions.InvalidAccountTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.MissingPropertyException;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public Account addAccount(Account newAccount, String userId) throws MissingPropertyException {
            Optional<User> user = userRepo.findById(userId);
        Transaction newTransaction = new Transaction();
            if (user.isPresent()){
                newAccount.setAccountopened(LocalDateTime.now());
                user.get().getAccount().add(newAccount);
                accountRepo.insert(newAccount);
                userRepo.save(user.get());
                newTransaction.setTransactionType(TransactionType.DEPOSIT);
                newTransaction.setDescription("initial deposit of $" + String.format("%.2f",user.get().getAccount().get(user.get().getAccount().size()-1).getBalance()));
                newTransaction.setTransactionDate(LocalDateTime.now());
                user.get().getTransaction().push(newTransaction);
                transactionRepo.insert(newTransaction);
                userRepo.save(user.get());
            } else
                throw new MissingPropertyException("user does not exist");

            return newAccount;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public List<Account> getAccountsByUserId(String userId) throws InvalidUserIdException {
        User retrieved = null;
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            retrieved = user.get();
            return retrieved.getAccount();
        }
        else{
            throw new InvalidUserIdException("User with that id does not exist");
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
