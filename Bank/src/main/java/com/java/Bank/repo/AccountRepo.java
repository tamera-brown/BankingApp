package com.java.Bank.repo;

import com.java.Bank.AccountType;
import com.java.Bank.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepo extends MongoRepository<Account,String> {
    @Query("{accountType:'?0'}")
    List<Account> findAccountsByType(AccountType accountType);
}
