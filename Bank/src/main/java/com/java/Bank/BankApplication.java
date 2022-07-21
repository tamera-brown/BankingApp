package com.java.Bank;

import com.java.Bank.model.User;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class BankApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User c1 = new User();
		c1.setFirstName("Tamera");
		c1.setLastName("Brown");
		c1.setEmail("tamerabrown642@gmail.com");
		c1.setAddress("101 Water Street Riverdaile, MD 20098");
		c1.setPhoneNum("2403748638");

		userRepo.save(c1);


		System.out.println("************************");

		List<User> users = userRepo.findAll();

		for (User c : users) {
			System.out.println(c.toString());
		}

	}
}
