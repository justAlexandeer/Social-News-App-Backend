package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseFullPost;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import com.github.justalexandeer.SocialNewsAppBackend.service.*;
import com.github.justalexandeer.SocialNewsAppBackend.util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

// Исправлять все контролеры на возвращаемые значения
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final AnswerService answerService;
    private final UserService userService;
    private final PermissionManager permissionManager;

    @Autowired
    public PostController(
            PostService postService,
            CommentService commentService,
            AnswerService answerService,
            UserService userService,
            PermissionManager permissionManager
    ) {
        this.postService = postService;
        this.commentService = commentService;
        this.answerService = answerService;
        this.userService = userService;
        this.permissionManager = permissionManager;
    }

    @GetMapping("/createPost")
    public ResponseEntity<Void> createPost(
            HttpServletRequest request,
            @RequestParam(value = "postName") String postName,
            @RequestParam(value = "postContent") String postContent,
            @RequestParam(value = "categoryId") String categoryId,
            @RequestParam(value = "tagsId", required = false) String tagsId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String appUserName = permissionManager.getUserName(authorizationHeader);
        postService.createPost(appUserName, postName, postContent, categoryId, tagsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/changePost")
    public ResponseEntity<Void> changePost(
            HttpServletRequest request,
            @RequestParam(value = "postId") String postId,
            @RequestParam(value = "postName", required = false) String postName,
            @RequestParam(value = "postContent", required = false) String postContent
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.havePermissionToChangePost(authorizationHeader, postId)) {
            postService.changePost(postId, postName, postContent);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/deletePost")
    public ResponseEntity<Void> deletePost(
            @RequestParam(value = "postId") String postId
    ) {
        postService.deletePost(Long.valueOf(postId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getPost")
    public ResponseEntity<Response<ResponseFullPost>> getPost(
            @RequestParam(value = "postId") String postId
    ) {
        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/getPosts")
    public ResponseEntity<Response<Page<ResponseSimplePost>>> getAllPostBySearchCriteriaAndSort(
            @RequestParam(value = "afterPostDate", required = false) String afterPostDate,
            @RequestParam(value = "beforePostDate", required = false) String beforePostDate,
            @RequestParam(value = "nameAuthor", required = false) String nameAuthor,
            @RequestParam(value = "idCategory", required = false) String idCategory,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "tagsIn", required = false) String tagsIn,
//            @RequestParam(value = "tagsAll", required = false) String tagsAll,
            @RequestParam(value = "postName", required = false) String postName,
            @RequestParam(value = "postContent", required = false) String postContent,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "default") String sortBy
    ) {
        HashMap<String, String> mapParams = new HashMap<>();
        mapParams.put("afterPostDate", afterPostDate);
        mapParams.put("beforePostDate", beforePostDate);
        mapParams.put("nameAuthor", nameAuthor);
        mapParams.put("idCategory", idCategory);
        mapParams.put("tag", tag);
        mapParams.put("tagsIn", tagsIn);
//        mapParams.put("tagsAll", tagsAll);
        mapParams.put("postName", postName);
        mapParams.put("postContent", postContent);

        return new ResponseEntity<>(
                (postService.findAllPostBySearchCriteriaAndSort(mapParams, page, size, sortBy)),
                HttpStatus.OK
        );
    }
}
