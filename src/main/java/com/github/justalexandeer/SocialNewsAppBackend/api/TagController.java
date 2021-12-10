package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/addTagToPost")
    public ResponseEntity<String> addTagToPost(
            @RequestParam String postId,
            @RequestParam String tagId
    ) {
        tagService.addTagToPost(Long.valueOf(postId), Long.valueOf(tagId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
