package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.service.TagService;
import com.github.justalexandeer.SocialNewsAppBackend.util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;
    private final PermissionManager permissionManager;

    @Autowired
    public TagController(
            TagService tagService,
            PermissionManager permissionManager) {
        this.tagService = tagService;
        this.permissionManager = permissionManager;
    }

    @GetMapping("/getTags")
    public ResponseEntity<List<Tag>> getCategories() {
        List<Tag> listOfTags = tagService.findAllTags();
        return new ResponseEntity<>(listOfTags, HttpStatus.OK);
    }

    @GetMapping("/setTagToPost")
    public ResponseEntity<String> setTagToPost(
            HttpServletRequest request,
            @RequestParam String postId,
            @RequestParam String tagId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(permissionManager.havePermissionToChangePost(authorizationHeader, postId)) {
            tagService.setTagToPost(postId, tagId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/createTag")
    public ResponseEntity<Void> createTag(
            @RequestParam(value = "tagName") String tagName
    ) {
        tagService.createTag(tagName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/changeTag")
    public ResponseEntity<Void> changeTag(
            HttpServletRequest request,
            @RequestParam(value = "tagId") String tagId,
            @RequestParam(value = "tagName") String tagName
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.isAdmin(authorizationHeader)) {
            tagService.changeTag(tagId, tagName);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/deleteTag")
    public ResponseEntity<Void> deleteTag(
            HttpServletRequest request,
            @RequestParam(value = "tagId") String tagId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.isAdmin(authorizationHeader)) {
            tagService.deleteTag(tagId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
