package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void savePost(Post post);
    Page<ResponseSimplePost> findAllPosts(int size, int page);
    List<Post> findAllPostBySearchCriteria(HashMap<String, String> mapParams);
}
