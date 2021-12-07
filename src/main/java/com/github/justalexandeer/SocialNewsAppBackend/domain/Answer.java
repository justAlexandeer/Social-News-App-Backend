package com.github.justalexandeer.SocialNewsAppBackend.domain;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Answer() {

    }

    public Answer(Long id, String content, Comment comment) {
        this.id = id;
        this.content = content;
        this.comment = comment;
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
}
