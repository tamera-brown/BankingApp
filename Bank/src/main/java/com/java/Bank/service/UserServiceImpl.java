package com.java.Bank.service;

import com.java.Bank.AccountStatus;
import com.java.Bank.Authority;
import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    static Account newAccount=new Account();
    static Transaction newTransaction=new Transaction();

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionRepo transactionRepo;;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(User newUser) throws UniqueUserEmailException, NullUserObjectException {
        if(newUser==null){
            throw new NullUserObjectException("User cann ont be null");
        }

        Optional<User> user = userRepo.findUserByEmail(newUser.getEmail());
        if(user.isEmpty()){
            List<Account> allAccounts=new ArrayList<>();
            Stack<Transaction> allTransactions=new Stack<>();
            newAccount.setAccountType(newUser.getAccount().get(0).getAccountType());
            newAccount.setBalance(newUser.getAccount().get(0).getBalance());
            newAccount.setAccountStatus(AccountStatus.ACTIVE);
            allAccounts.add(newAccount);
            newUser.setAccount(allAccounts);
            newTransaction.setTransactionType(TransactionType.DEPOSIT);
            newTransaction.setTransactionAmount(newUser.getAccount().get(0).getBalance());
            newTransaction.setDescription("initial deposit of $" + String.format("%.2f",newTransaction.getTransactionAmount()));
            allTransactions.push(newTransaction);
            newAccount.setTransaction(allTransactions);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setAuthorities(List.of(Authority.ROLE_USER));
            transactionRepo.insert(allTransactions);
            accountRepo.insert(allAccounts);
            userRepo.insert(newUser);

        }
        else if(user.isPresent()) {
            throw new UniqueUserEmailException("email already exist");

        }
        return newUser;

    }

    @Override
    public User updateUser(User updateUser) {
        return userRepo.save(updateUser);
    }

    @Override
    public void deleteUserById(String id) throws InvalidUserIdException {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            throw new InvalidUserIdException("User with that id does not exist");
        }
    }

    @Override
    public User getUserByEmail(String email) throws InvalidUserEmailException {
        User retrieved = null;
        Optional<User> user = userRepo.findUserByEmail(email);
        if (user.isPresent()) {
            retrieved = user.get();
            return retrieved;
        } else {
            throw new InvalidUserEmailException("User with that email does not exist");
        }

    }


    @Override
    public User getUserById(String id) throws InvalidUserIdException {
        User retrieved = null;
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            retrieved = user.get();
            return retrieved;
        }
        else{
            throw new InvalidUserIdException("User with that id does not exist");
        }
    }

    @Override
    public User getUserByUsername(String username) throws InvalidUsernameException {
            try{
               return userRepo.findUserByUsername(username);
            }catch (Exception e){
                throw new InvalidUsernameException("User with that username does not exist");
            }
    }

}

