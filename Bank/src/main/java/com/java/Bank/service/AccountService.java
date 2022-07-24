package com.java.Bank.service;

import com.java.Bank.exceptions.InvalidAccountIdException;
import com.java.Bank.exceptions.InvalidAccountTypeException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.MissingPropertyException;
import com.java.Bank.model.Account;

import java.util.List;

public interface AccountService {

    Account addAccount(Account newAccount, String userId) throws MissingPropertyException;

    List<Account> getAllAccounts();

    List<Account>getAccountsByUserId(String userId) throws InvalidUserIdException;

    List<Account> getAccountsByAccountType(String type) throws InvalidAccountTypeException;

    void deleteAccountById(String id) throws InvalidAccountIdException;
}
