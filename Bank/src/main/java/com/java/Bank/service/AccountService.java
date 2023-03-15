package com.java.Bank.service;

import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;

import java.util.List;

public interface AccountService {

    Account addAccount(Account newAccount, String userId) throws MissingPropertyException;

    List<Account> getAllAccounts();

    List<Account>getAccountsByUsername(String username) throws InvalidUsernameException;

    List<Account> getAccountsByAccountType(String type) throws InvalidAccountTypeException;

    void closeAccountById(String id) throws InvalidAccountIdException;

    Account getAccountById(String id) throws InvalidAccountIdException;

    List<Account> getAccountsByAccountStatus(String status) throws InvalidAccountStatusException;
}
