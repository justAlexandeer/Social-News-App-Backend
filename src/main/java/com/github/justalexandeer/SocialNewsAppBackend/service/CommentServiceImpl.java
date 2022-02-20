package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseComment;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.CommentRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.UserRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final DataMapper dataMapper;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            UserRepository userRepository,
            PostRepository postRepository,
            AnswerRepository answerRepository,
            DataMapper dataMapper
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.answerRepository = answerRepository;
        this.dataMapper = dataMapper;
    }

    @Override
    public Page<ResponseComment> getComments(String postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Post post = postRepository.getById(Long.valueOf(postId));
        Page<Comment> pageOfComments = commentRepository.findAllByPost(post, pageable);
        return dataMapper.mapFromPageCommentToPageResponseComment(pageOfComments, answerRepository);
    }

    @Override
    public void createComment(String appUserName, String commentContent, String postId) {
        AppUser appUser = userRepository.findByUsername(appUserName);
        Post post = postRepository.getById(Long.valueOf(postId));
        post.setCommentCount(post.getCommentCount() + 1);
        Comment comment = new Comment(null, commentContent, post, appUser);
        commentRepository.save(comment);
    }

    @Override
    public void changeComment(String commentId, String commentContent) {
        Comment comment = commentRepository.getById(Long.valueOf(commentId));
        comment.setContent(commentContent);
    }

    @Override
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.getById(Long.valueOf(commentId));
        Post post = comment.getPost();
        post.setCommentCount(post.getCommentCount() - 1);
        answerRepository.deleteAllByComment(comment);
        commentRepository.delete(comment);
    }
}
