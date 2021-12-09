package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.PostSearchCriteria;
import com.github.justalexandeer.SocialNewsAppBackend.util.PostSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Page<ResponseSimplePost> findAllPosts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> pageOfPost = postRepository.findAllPosts(pageable);
        Page<ResponseSimplePost> pageOfResponseSimplePost = pageOfPost.map(post -> {
            ResponseAppUser responseAppUser = new ResponseAppUser(
                    post.getAppUser().getId(),
                    post.getAppUser().getName(),
                    post.getAppUser().getUsername()
            );
            ResponseSimplePost responseSimplePost = new ResponseSimplePost(
                    post.getId(),
                    post.getName(),
                    post.getDate(),
                    responseAppUser,
                    post.getCategory(),
                    post.getTags(),
                    post.getContent()
            );
            return responseSimplePost;
        });
        return pageOfResponseSimplePost;
    }

    @Override
    public List<Post> findAllPostBySearchCriteria(HashMap<String, String> mapParams) {
        HashMap<PostSearchCriteria, String> mapCriteria = new HashMap<>();
        mapParams.forEach((name, value) -> {
            mapCriteria.put(PostSearchCriteria.fromParamStringToPostSearchCriteria(name), value);
        });
        PostSpecificationBuilder postSpecificationBuilder = new PostSpecificationBuilder();
        mapCriteria.forEach((key, value) -> {
            postSpecificationBuilder.with(key, value);
            System.out.println(key + " " + value);
        });

        return postRepository.findAll(postSpecificationBuilder.build());


        //return postRepository.findAll(PostSpecifications.isBeforeDate(date).and(PostSpecifications.isBeforeDate(date)));
    }
}
