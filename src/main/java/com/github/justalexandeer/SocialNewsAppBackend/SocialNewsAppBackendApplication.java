package com.github.justalexandeer.SocialNewsAppBackend;

import com.github.justalexandeer.SocialNewsAppBackend.domain.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.Role;
import com.github.justalexandeer.SocialNewsAppBackend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SocialNewsAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNewsAppBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//			userService.saveUser(new AppUser(null, "Jonh Piter", "john", "1234", new ArrayList<>()));
//			userService.saveUser(new AppUser(null, "Sergey Victor", "sergey", "4321", new ArrayList<>()));

		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
