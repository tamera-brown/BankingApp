package com.java.Bank.service;

import com.java.Bank.TransactionType;
import com.java.Bank.exceptions.InvalidUserEmailException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.NullUserObjectException;
import com.java.Bank.exceptions.UniqueUserEmailException;
import com.java.Bank.model.Account;
import com.java.Bank.model.Transaction;
import com.java.Bank.model.User;
import com.java.Bank.repo.AccountRepo;
import com.java.Bank.repo.TransactionRepo;
import com.java.Bank.repo.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class UserServiceImpl implements UserService{
    static Account newAccount=new Account();
    static Transaction newTransaction=new Transaction();

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
    public User addUser(User newUser) throws UniqueUserEmailException, NullUserObjectException {
        if(newUser==null){
            throw new NullUserObjectException("User cann ont be null");
        }

        Optional<User> user = userRepo.findUserByEmail(newUser.getEmail());
        if(user.isEmpty()){
            List<Account> allAccounts=new ArrayList<>();
            Stack<Transaction> allTransactions=new Stack<>();
            newAccount.setAccountType(newUser.getAccount().get(0).getAccountType());
            newAccount.setBalance(newUser.getAccount().get(0).getBalance());
            newAccount.setAccountopened(LocalDateTime.now());
            allAccounts.add(newAccount);
            newUser.setAccount(allAccounts);
            newTransaction.setTransactionType(TransactionType.DEPOSIT);
            newTransaction.setDescription("initial deposit of $" + String.format("%.2f",newUser.getAccount().get(0).getBalance()));
            newTransaction.setTransactionDate(LocalDateTime.now());
            allTransactions.push(newTransaction);
            newUser.setTransaction(allTransactions);
            newUser.setPassword(hashPassword(newUser.getPassword()));;
            accountRepo.insert(allAccounts);
            transactionRepo.insert(allTransactions);
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

    private String hashPassword(String pwd){
        return BCrypt.hashpw(pwd,BCrypt.gensalt());
    }
}

