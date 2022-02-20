package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

public class ResponseSimplePost {
    private Long id;
    private String name;
    private Long date;
    private ResponseAppUser appUser;
    private Category category;
    private Collection<Tag> tags = new ArrayList<>();
    private String content;
    private int commentCount;

    public ResponseSimplePost(
            Long id,
            String name,
            Long date,
            ResponseAppUser appUser,
            Category category,
            Collection<Tag> tags,
            String content,
            int commentCount
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.appUser = appUser;
        this.category = category;
        this.tags = tags;
        this.content = content;
        this.commentCount = commentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public ResponseAppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ResponseAppUser appUser) {
        this.appUser = appUser;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
