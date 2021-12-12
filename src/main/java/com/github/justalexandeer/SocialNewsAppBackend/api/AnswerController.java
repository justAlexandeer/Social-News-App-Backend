package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.service.AnswerService;
import com.github.justalexandeer.SocialNewsAppBackend.util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class AnswerController {
    private final AnswerService answerService;
    private final PermissionManager permissionManager;

    @Autowired
    public AnswerController(AnswerService answerService, PermissionManager permissionManager) {
        this.answerService = answerService;
        this.permissionManager = permissionManager;
    }

    @GetMapping("/createAnswer")
    public ResponseEntity<Void> createAnswer(
            HttpServletRequest request,
            @RequestParam(value = "answerContent") String answerContent,
            @RequestParam(value = "commentId") String commentId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String appUserName = permissionManager.getUserName(authorizationHeader);
        answerService.createAnswer(appUserName, answerContent, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/changeAnswer")
    public ResponseEntity<Void> changeAnswer(
            HttpServletRequest request,
            @RequestParam(value = "answerId") String answerId,
            @RequestParam(value = "answerContent") String answerContent
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.havePermissionToChangeAnswer(authorizationHeader, answerId)) {
            answerService.changeAnswer(answerId, answerContent);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/deleteAnswer")
    public ResponseEntity<Void> changeAnswer(
            HttpServletRequest request,
            @RequestParam(value = "answerId") String answerId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.havePermissionToChangeAnswer(authorizationHeader, answerId)) {
            answerService.deleteAnswer(answerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
