package com.java.Bank.repo;

import com.java.Bank.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction,String> {
    @Query("{transactionType:'?0'}")
    List<Transaction> findTransactionByType(String transactionType);
}
