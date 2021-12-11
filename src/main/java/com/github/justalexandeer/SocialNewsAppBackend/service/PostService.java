package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseFullPost;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long id);

    void savePost(Post post);

    void deletePost(Long postId);

    void changePost(String postId, String postName, String postContent);

    Page<ResponseSimplePost> findAllPostBySearchCriteriaAndSort(
            HashMap<String, String> mapParams,
            int pageNumber,
            int size,
            String sortBy
    );

    ResponseFullPost getPost(String idPost);
}
