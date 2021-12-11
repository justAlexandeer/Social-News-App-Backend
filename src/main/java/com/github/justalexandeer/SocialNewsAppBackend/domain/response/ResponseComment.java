package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;

import javax.persistence.*;
import java.util.List;

public class ResponseComment {
    private Long id;
    private String content;
    private Long postId;
    private ResponseAppUser responseAppUser;
    private List<ResponseAnswer> responseAnswerList;

    public ResponseComment(
            Long id,
            String content,
            Long postId,
            ResponseAppUser responseAppUser,
            List<ResponseAnswer> responseAnswerList
    ) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.responseAppUser = responseAppUser;
        this.responseAnswerList = responseAnswerList;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public ResponseAppUser getResponseAppUser() {
        return responseAppUser;
    }

    public void setResponseAppUser(ResponseAppUser responseAppUser) {
        this.responseAppUser = responseAppUser;
    }

    public List<ResponseAnswer> getResponseAnswerList() {
        return responseAnswerList;
    }

    public void setResponseAnswerList(List<ResponseAnswer> responseAnswerList) {
        this.responseAnswerList = responseAnswerList;
    }
}
