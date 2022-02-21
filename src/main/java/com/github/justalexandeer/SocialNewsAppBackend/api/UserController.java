package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseTag;
import com.github.justalexandeer.SocialNewsAppBackend.service.UserService;
import com.github.justalexandeer.SocialNewsAppBackend.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Исправить на обновление Access Token подходит и сам AccessToken
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //String refresh_token = httpServletRequest.getHeader(AUTHORIZATION);
        String refresh_token = httpServletRequest.getParameter("refreshToken");
        if (refresh_token != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(Util.getSecretKey().getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String userName = decodedJWT.getSubject();
                AppUser user = userService.getAppUser(userName);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .withIssuer(httpServletRequest.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                Response<Map<String, String>> response = new Response<>("success", null, tokens);
                new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), response);
            } catch (Exception exception) {
                httpServletResponse.setHeader("error", exception.getMessage());
                httpServletResponse.setStatus(UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);
            }
        } else {
            // Исправить на нормальный httpServletResponse
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<Response<Void>> registerAppUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        return new ResponseEntity<>(userService.createAppUser(username, name, password), HttpStatus.OK);
    }

    @GetMapping("/getTopAuthors")
    public ResponseEntity<Response<List<ResponseAppUser>>> getTopTags(
            @RequestParam int limit
    ) {
        return new ResponseEntity<>(userService.getTopAuthors(limit), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return new ResponseEntity<>(userService.getAllAppUsers(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<AppUser> addUser() {
        return new ResponseEntity<>(userService.getAppUser("john"), HttpStatus.OK);
    }
}
