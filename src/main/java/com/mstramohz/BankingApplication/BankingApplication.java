package com.mstramohz.BankingApplication;

import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.enums.Role;
import com.mstramohz.BankingApplication.repository.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BankingApplication implements CommandLineRunner {
	@Autowired
	private AccountUserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AccountUser admin = new AccountUser();
		admin.setRole(Role.ADMIN);
		admin.setFirstname("Admin");
		admin.setLastname("Admin");
		admin.setUsername("mstramohz@gmail.com");
		admin.setPassword("Mstr@mohz1");
		admin.setPhoneNumber("07060727660");
		admin.setEnabled(true);
		if (userRepository.findByUsername("mstramohz@gmail.com").isEmpty()) userRepository.save(admin);
	}
}
