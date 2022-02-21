package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import javax.persistence.Column;

public class ResponseAppUser {
    private String name;
    private String username;
    private int amountOfPosts;

    public ResponseAppUser(String name, String username, int amountOfPosts) {
        this.name = name;
        this.username = username;
        this.amountOfPosts = amountOfPosts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAmountOfPosts() {
        return amountOfPosts;
    }

    public void setAmountOfPosts(int amountOfPosts) {
        this.amountOfPosts = amountOfPosts;
    }
}
