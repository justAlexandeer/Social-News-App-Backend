package com.github.justalexandeer.SocialNewsAppBackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.service.AnswerService;
import com.github.justalexandeer.SocialNewsAppBackend.service.PostService;
import com.github.justalexandeer.SocialNewsAppBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("permissionManager")
public class PermissionManager {
    private final PostService postService;
    private final UserService userService;
    private final AnswerService answerService;

    @Autowired
    public PermissionManager(PostService postService, UserService userService, AnswerService answerService) {
        this.postService = postService;
        this.userService = userService;
        this.answerService = answerService;
    }

    public boolean havePermissionToChangePost(String authorizationHeader, String idPost) {
        if(isAdmin(authorizationHeader))
            return true;
        Post post = postService.getPostById(Long.valueOf(idPost));
        AppUser appUser = userService.getAppUser(getUserName(authorizationHeader));
        return post.getAppUser().getUsername().equals(appUser.getUsername());
    }

    public boolean havePermissionToChangeAnswer(String authorizationHeader, String idAnswer) {
        if(isAdmin(authorizationHeader))
            return true;
        Answer answer = answerService.getAnswerById(idAnswer);
        AppUser appUser = userService.getAppUser(getUserName(authorizationHeader));
        return answer.getAppUser().getUsername().equals(appUser.getUsername());
    }

    public String getUserName(String authorizationHeader) {
        Algorithm algorithm = Algorithm.HMAC256(Util.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(authorizationHeader);
        return decodedJWT.getSubject();
    }

    public boolean isAdmin(String authorizationHeader) {
        List<String> listRoles = getArrayOfRoles(authorizationHeader);
        for(String role: listRoles) {
            if (role.equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private List<String> getArrayOfRoles(String authorizationHeader) {
        Algorithm algorithm = Algorithm.HMAC256(Util.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(authorizationHeader);
        String userName = decodedJWT.getSubject();
        return decodedJWT.getClaim("roles").asList(String.class);
    }

}
