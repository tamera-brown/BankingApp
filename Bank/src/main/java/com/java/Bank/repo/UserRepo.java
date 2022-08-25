package com.java.Bank.repo;

import com.java.Bank.model.Account;
import com.java.Bank.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    @Query("{'email':?0}")
    Optional<User> findUserByEmail(String email);

    @Query("{'username':?0}")
    User findUserByUsername(String username);




}
