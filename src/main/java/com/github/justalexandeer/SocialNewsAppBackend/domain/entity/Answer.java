package com.github.justalexandeer.SocialNewsAppBackend.domain.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @OneToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    public Answer() {

    }

    public Answer(Long id, String content, Comment comment, AppUser appUser) {
        this.id = id;
        this.content = content;
        this.comment = comment;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
