package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;

import javax.persistence.*;

public class ResponseAnswer {
    private Long id;
    private String content;
    private Long commentId;
    private ResponseAppUser appUser;

    public ResponseAnswer(Long id, String content, Long commentId, ResponseAppUser appUser) {
        this.id = id;
        this.content = content;
        this.commentId = commentId;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public ResponseAppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ResponseAppUser appUser) {
        this.appUser = appUser;
    }
}
