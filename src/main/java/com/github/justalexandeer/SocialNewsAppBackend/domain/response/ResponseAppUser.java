package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import javax.persistence.Column;

public class ResponseAppUser {
    private String name;
    private String username;

    public ResponseAppUser(String name, String username) {
        this.name = name;
        this.username = username;
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
}
