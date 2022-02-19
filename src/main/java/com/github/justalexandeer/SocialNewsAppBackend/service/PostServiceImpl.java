package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.*;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseFullPost;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.CommentRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.TagRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final UserServiceImpl userService;
    private final CategoryService categoryService;
    private final DataMapper dataMapper;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            DataMapper dataMapper,
            CommentRepository commentRepository,
            AnswerRepository answerRepository,
            CategoryService categoryService,
            UserServiceImpl userService,
            TagRepository tagRepository
    ) {
        this.postRepository = postRepository;
        this.dataMapper = dataMapper;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.tagRepository = tagRepository;
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
    public void deletePost(Long postId) {
        Post post = postRepository.getById(postId);
        List<Comment> commentList = commentRepository.findAllByPost(post);
        commentList.forEach(comment -> {
            answerRepository.deleteAllByComment(comment);
            commentRepository.delete(comment);
        });
        postRepository.delete(post);
    }

    @Override
    public void changePost(String postId, String postName, String postContent) {
        Post post = postRepository.getById(Long.valueOf(postId));
        if (postName != null && !postName.equals("")) {
            post.setName(postName);
        }
        if (postContent != null && !postContent.equals("")) {
            post.setContent(postContent);
        }
    }

    @Override
    public void createPost(String appUserName, String postName, String postContent, String categoryId, String tagsId) {
        AppUser appUser = userService.getAppUser(appUserName);
        Category category = categoryService.getCategoryById(Long.valueOf(categoryId));
        List<String> listOfTagsId = new ArrayList(Arrays.asList(tagsId.split(",")));
        List<Tag> listOfTags = new ArrayList<>();
        for (String tagId : listOfTagsId) {
            Tag tag = tagRepository.getById(Long.valueOf(tagId));
            listOfTags.add(tag);
        }
        Post post = new Post(
                null,
                postName,
                System.currentTimeMillis(),
                appUser,
                category,
                listOfTags,
                postContent
        );
        postRepository.save(post);
    }

    @Override
    public Response<Page<ResponseSimplePost>> findAllPostBySearchCriteriaAndSort(
            HashMap<String, String> mapParams,
            int pageNumber,
            int size,
            String sortBy
    ) {
        try {
            HashMap<PostSearchCriteria, String> mapCriteria = new HashMap<>();
            mapParams.forEach((name, value) -> {
                mapCriteria.put(PostSearchCriteria.fromParamStringToPostSearchCriteria(name), value);
            });
            PostSpecificationBuilder postSpecificationBuilder = new PostSpecificationBuilder();
            mapCriteria.forEach(postSpecificationBuilder::with);
            Sort sort = PostSortBy.fromPostSortByToSort(PostSortBy.fromParamStringToPostSortBy(sortBy));
            Pageable pageable = PageRequest.of(pageNumber, size, sort);

            Page<Post> page = postRepository.findAll(postSpecificationBuilder.build(), pageable);

            return new Response<>("success", null ,dataMapper.mapPagePostToPageSimplePost(page));
        } catch (Exception exception) {
            return new Response<>("error", exception.getLocalizedMessage(), null);
        }
    }

    @Override
    public Response<ResponseFullPost> getPost(String idPost) {
        Pageable pageable = PageRequest.of(0, 20);
        try {
            Post post = postRepository.getById(Long.valueOf(idPost));
            Page<Comment> pageOfComments = commentRepository.findAllByPost(post, pageable);
            return new Response<>(
                    "success",
                    null,
                    dataMapper.mapToResponseFullPost(post, pageOfComments, answerRepository)
            );
        } catch (EntityNotFoundException exception) {
            return new Response<>("error", "noSuchPost", null);
        }
    }
}
