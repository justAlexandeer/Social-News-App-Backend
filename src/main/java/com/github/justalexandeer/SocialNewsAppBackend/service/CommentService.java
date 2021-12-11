package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentsByPost(Post post);
}
