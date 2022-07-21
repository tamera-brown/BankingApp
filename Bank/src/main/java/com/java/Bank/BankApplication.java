package com.java.Bank;

import com.java.Bank.model.User;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class BankApplication {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}


}
