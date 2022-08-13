package com.java.Bank.repo;

import com.java.Bank.AccountType;
import com.java.Bank.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface AccountRepo extends MongoRepository<Account,String> {
    @Query("{'accountType':?0}")
    List<Account> findAccountsByType(AccountType accountType);

}
