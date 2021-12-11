package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseFullPost;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseSimplePost;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.CommentRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.DataMapper;
import com.github.justalexandeer.SocialNewsAppBackend.util.PostSearchCriteria;
import com.github.justalexandeer.SocialNewsAppBackend.util.PostSortBy;
import com.github.justalexandeer.SocialNewsAppBackend.util.PostSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final DataMapper dataMapper;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            DataMapper dataMapper,
            CommentRepository commentRepository,
            AnswerRepository answerRepository
    ) {
        this.postRepository = postRepository;
        this.dataMapper = dataMapper;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
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
    public Page<ResponseSimplePost> findAllPostBySearchCriteriaAndSort(
            HashMap<String, String> mapParams,
            int pageNumber,
            int size,
            String sortBy
    ) {
        HashMap<PostSearchCriteria, String> mapCriteria = new HashMap<>();
        mapParams.forEach((name, value) -> {
            mapCriteria.put(PostSearchCriteria.fromParamStringToPostSearchCriteria(name), value);
        });
        PostSpecificationBuilder postSpecificationBuilder = new PostSpecificationBuilder();
        mapCriteria.forEach(postSpecificationBuilder::with);
        Sort sort = PostSortBy.fromPostSortByToSort(PostSortBy.fromParamStringToPostSortBy(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, size, sort);

        Page<Post> page = postRepository.findAll(postSpecificationBuilder.build(), pageable);

        return dataMapper.mapPagePostToPageSimplePost(page);
    }

    @Override
    public ResponseFullPost getPost(String idPost) {
        Pageable pageable = PageRequest.of(0, 20);
        Post post = postRepository.getById(Long.valueOf(idPost));
        Page<Comment> pageOfComments = commentRepository.findAll(pageable);

        return dataMapper.mapToResponseFullPost(post, pageOfComments, answerRepository);
    }
}
