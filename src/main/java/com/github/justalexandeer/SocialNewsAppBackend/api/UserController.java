package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAppUsers(){
        return new ResponseEntity<>(userService.getAllAppUsers(), HttpStatus.OK);
    }
}
