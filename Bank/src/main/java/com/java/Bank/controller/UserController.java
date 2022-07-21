package com.java.Bank.controller;

import com.java.Bank.exceptions.InvalidUserEmailException;
import com.java.Bank.exceptions.InvalidUserIdException;
import com.java.Bank.exceptions.NullUserObjectException;
import com.java.Bank.exceptions.UniqueUserEmailException;
import com.java.Bank.model.User;
import com.java.Bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService service;


    @PostMapping("/AddUser")
    public ResponseEntity addUser(@RequestBody User newUser) {
        try {
            return new ResponseEntity(service.addUser(newUser), HttpStatus.CREATED);
        }catch (UniqueUserEmailException | NullUserObjectException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity getAllUsers(){
        logger.info("Getting all Users.");return ResponseEntity.ok(service.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        try {
            return  ResponseEntity.ok(service.getUserById(id));
        }catch(InvalidUserIdException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email){
        try{
            return ResponseEntity.ok(service.getUserByEmail(email));
        }catch (InvalidUserEmailException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User updateUser){
        return ResponseEntity.ok(service.updateUser(updateUser));
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id){
        try {
            service.deleteUserById(id);
            return new ResponseEntity("User  successfully deleted", HttpStatus.NO_CONTENT);
        }catch(InvalidUserIdException ex){
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
