package com.github.justalexandeer.SocialNewsAppBackend.domain.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "content")
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;
    @OneToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    public Comment() {

    }

    public Comment(Long id, String content, Post post, AppUser appUser) {
        this.id = id;
        this.content = content;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
