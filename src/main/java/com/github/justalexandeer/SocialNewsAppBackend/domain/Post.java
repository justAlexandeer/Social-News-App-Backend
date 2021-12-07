package com.github.justalexandeer.SocialNewsAppBackend.domain;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_creation")
    private Long date;
    @OneToOne
    @JoinColumn(name ="app_user_id")
    private AppUser appUser;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Collection<Tag> tags = new ArrayList<>();
    @Column(name = "content")
    private String content;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Collection<Comment> comments = new ArrayList<>();

    public Post() {

    }

    public Post(
            Long id,
            String name,
            Long date,
            AppUser appUser,
            Category category,
            Collection<Tag> tags,
            String content,
            Collection<Comment> comments
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.appUser = appUser;
        this.category = category;
        this.tags = tags;
        this.content = content;
        this.comments = comments;
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
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

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
