package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import java.util.List;

public class ResponseComment {
    private Long id;
    private String content;
    private Long postId;
    private ResponseAppUser appUser;
    private List<ResponseAnswer> answerList;

    public ResponseComment(
            Long id,
            String content,
            Long postId,
            ResponseAppUser appUser,
            List<ResponseAnswer> answerList
    ) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.appUser = appUser;
        this.answerList = answerList;
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

    public ResponseAppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ResponseAppUser appUser) {
        this.appUser = appUser;
    }

    public List<ResponseAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<ResponseAnswer> answerList) {
        this.answerList = answerList;
    }
}
