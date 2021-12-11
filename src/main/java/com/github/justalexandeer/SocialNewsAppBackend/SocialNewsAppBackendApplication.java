package com.github.justalexandeer.SocialNewsAppBackend;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.service.*;
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
			AnswerService answerService,
			TagService tagService
	) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			final AppUser appUser1 = new AppUser(null, "Jonh Piter", "john", "1234", new ArrayList<>());
			userService.saveUser(appUser1);
			final AppUser appUser2 = new AppUser(null, "Sergey Victor", "sergey", "4321", new ArrayList<>());
			userService.saveUser(appUser2);
			final AppUser appUser3 = new AppUser(null, "AAAA", "asdfasdf", "43210", new ArrayList<>());
			userService.saveUser(appUser3);

			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("sergey", "ROLE_ADMIN");

			Post post1 = new Post(
					1L,
					"First post of this project",
					System.currentTimeMillis(),
					appUser1,
					null,
					null,
					"content null content content content content content content content content content content"
			);
            Post post2 = new Post(
                    1L,
                    "Second post of this project",
					1639201012000L,
                    appUser2,
                    null,
                    null,
                    "no content"
            );
			Post post3 = new Post(
					1L,
					"Second post of this project",
					System.currentTimeMillis(),
					appUser3,
					null,
					null,
					"no content"
			);
			postService.savePost(post1);
            postService.savePost(post2);
            postService.savePost(post3);

			tagService.saveTag(new Tag(100L, "first Tag"));
			tagService.saveTag(new Tag(101L, "Second Tag"));
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
