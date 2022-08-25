package com.java.Bank.service;

import com.java.Bank.exceptions.*;
import com.java.Bank.model.Account;

import java.util.List;

public interface AccountService {

    Account addAccount(Account newAccount, String userId) throws MissingPropertyException;

    List<Account> getAllAccounts();

    List<Account>getAccountsByUsername(String username) throws InvalidUsernameException;

    List<Account> getAccountsByAccountType(String type) throws InvalidAccountTypeException;

    void deleteAccountById(String id) throws InvalidAccountIdException;
}
