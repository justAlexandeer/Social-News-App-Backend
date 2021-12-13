package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseComment;
import com.github.justalexandeer.SocialNewsAppBackend.service.CommentService;
import com.github.justalexandeer.SocialNewsAppBackend.util.PermissionManager;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final PermissionManager permissionManager;

    public CommentController(CommentService commentService, PermissionManager permissionManager) {
        this.commentService = commentService;
        this.permissionManager = permissionManager;
    }

    @GetMapping("/getComments")
    public ResponseEntity<Page<ResponseComment>> getComments(
            @RequestParam String postId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return new ResponseEntity<>(commentService.getComments(postId, page, size), HttpStatus.OK);
    }

    @GetMapping("/createComment")
    public ResponseEntity<Void> createComment(
            HttpServletRequest request,
            @RequestParam(value = "commentContent") String commentContent,
            @RequestParam(value = "postId") String postId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String appUserName = permissionManager.getUserName(authorizationHeader);
        commentService.createComment(appUserName, commentContent, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/changeComment")
    public ResponseEntity<Void> changeComment(
            HttpServletRequest request,
            @RequestParam(value = "commentId") String commentId,
            @RequestParam(value = "commentContent") String commentContent
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.havePermissionToChangeComment(authorizationHeader, commentId)) {
            commentService.changeComment(commentId, commentContent);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/deleteComment")
    public ResponseEntity<Void> deleteComment(
            HttpServletRequest request,
            @RequestParam(value = "commentId") String commentId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.havePermissionToChangeComment(authorizationHeader, commentId)) {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
