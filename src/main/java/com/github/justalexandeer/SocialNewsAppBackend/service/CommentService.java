package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseComment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    Page<ResponseComment> getComments(String postId, int page, int size);
    void createComment(String appUserName, String commentContent, String postId);
    void changeComment(String commentId, String commentContent);
    void deleteComment(String commentId);
}
