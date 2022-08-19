package com.java.Bank.service;

import com.java.Bank.exceptions.InvalidUserEmailException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.NullUserObjectException;
import com.java.Bank.exceptions.UniqueUserEmailException;
import com.java.Bank.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User createUser(User newUser) throws UniqueUserEmailException, NullUserObjectException;

    User updateUser(User updateUser);

    void deleteUserById(String id) throws InvalidUserIdException;

    User getUserByEmail(String email) throws InvalidUserEmailException;

    User getUserById(String id) throws InvalidUserIdException;

    User getUserByUsername(String username) throws InvalidUserEmailException;
}
