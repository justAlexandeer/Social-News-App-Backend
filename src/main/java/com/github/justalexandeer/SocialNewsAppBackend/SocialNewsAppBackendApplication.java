package com.github.justalexandeer.SocialNewsAppBackend;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;
import com.github.justalexandeer.SocialNewsAppBackend.service.AnswerService;
import com.github.justalexandeer.SocialNewsAppBackend.service.CommentService;
import com.github.justalexandeer.SocialNewsAppBackend.service.PostService;
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
	CommandLineRunner run(
			UserService userService,
			PostService postService,
			CommentService commentService,
			AnswerService answerService
	) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			final AppUser appUser = new AppUser(null, "Jonh Piter", "john", "1234", new ArrayList<>());
			userService.saveUser(appUser);
			userService.saveUser(new AppUser(null, "Sergey Victor", "sergey", "4321", new ArrayList<>()));

			userService.addRoleToUser("john", "ROLE_USER");

			Post post = new Post(
					1L,
					"First post of this project",
					System.currentTimeMillis(),
					appUser,
					null,
					null,
					"content null content content content content content content content content content content"
			);

			postService.savePost(post);
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
