package com.github.justalexandeer.SocialNewsAppBackend.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "content")
    private String content;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private Collection<Answer> answers = new ArrayList<>();

    public Comment() {

    }

    public Comment(Long id, String content, Collection<Answer> answers) {
        this.id = id;
        this.content = content;
        this.answers = answers;
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

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }
}
