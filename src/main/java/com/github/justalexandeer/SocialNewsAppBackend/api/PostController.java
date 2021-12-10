package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import com.github.justalexandeer.SocialNewsAppBackend.service.CommentService;
import com.github.justalexandeer.SocialNewsAppBackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponseSimplePost>> findAllPost(
            @RequestParam int size,
            @RequestParam int page,
            @RequestParam long dateAfter
    ) {
        return new ResponseEntity<>(postService.findAllPosts(size, page), HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<Page<Post>> findPostByName(
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
            @RequestParam int size
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


        return new ResponseEntity<>((postService.findAllPostBySearchCriteriaAndSort(mapParams, page, size)), HttpStatus.OK);
    }


//    @GetMapping("/posts")
//    public ResponseEntity<List<Post>> getAllPosts() {
//        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
//    }

}
