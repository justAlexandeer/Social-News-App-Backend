package com.github.justalexandeer.SocialNewsAppBackend;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.*;
import com.github.justalexandeer.SocialNewsAppBackend.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SocialNewsAppBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SocialNewsAppBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SocialNewsAppBackendApplication.class);
	}

	@Bean
	CommandLineRunner run(
			UserService userService,
			PostService postService,
			CommentService commentService,
			AnswerService answerService,
			CategoryService categoryService,
			TagService tagService
	) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			final AppUser appUser1 = new AppUser("Jonh Piter", "john", "1234", new ArrayList<>());
			userService.saveUser(appUser1);
			final AppUser appUser2 = new AppUser("Sergey Victor", "sergey", "4321", new ArrayList<>());
			userService.saveUser(appUser2);
			final AppUser appUser3 = new AppUser("AAAA", "asdfasdf", "43210", new ArrayList<>());
			userService.saveUser(appUser3);

			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("sergey", "ROLE_ADMIN");

			tagService.saveTag(new Tag(1L, "first Tag", 0));
			tagService.saveTag(new Tag(1L, "Second Tag", 0));

			categoryService.createDefaultCategory("Politics");
			categoryService.createDefaultCategory("Sport");
			categoryService.createDefaultCategory("Finance");
			categoryService.createDefaultCategory("Auto");
			categoryService.createDefaultCategory("Style");
			categoryService.createCategory("Investments");
			categoryService.createCategory("Conferences");

			List<Category> listDefaultCategory = categoryService.findAllDefaultCategories().getData();

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF Politics",
						System.currentTimeMillis(),
						appUser1,
						listDefaultCategory.get(0),
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF Sport",
						System.currentTimeMillis(),
						appUser1,
						listDefaultCategory.get(1),
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF Finance",
						System.currentTimeMillis(),
						appUser1,
						listDefaultCategory.get(2),
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF Auto",
						System.currentTimeMillis(),
						appUser1,
						listDefaultCategory.get(3),
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF Style",
						System.currentTimeMillis(),
						appUser1,
						listDefaultCategory.get(4),
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

			for (int i = 4; i >= 0; i--) {
				postService.savePost(new Post(
						1L,
						i + " OF not default",
						System.currentTimeMillis(),
						appUser1,
						null,
						null,
						"content null content content content content content content content content content content",
						0
				));
			}

		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
