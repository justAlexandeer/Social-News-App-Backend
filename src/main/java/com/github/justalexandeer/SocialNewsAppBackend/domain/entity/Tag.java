package com.github.justalexandeer.SocialNewsAppBackend.domain.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "tags")
    @Column(name = "posts")
    private Collection<Post> posts = new ArrayList<>();
    @Column(name = "amount_of_use")
    private int amountOfUse;
    public Tag() {
    }

    public Tag(Long id, String name, int amountOfUse) {
        this.id = id;
        this.name = name;
        this.amountOfUse = amountOfUse;
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

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public int getAmountOfUse() {
        return amountOfUse;
    }

    public void setAmountOfUse(int amountOfUse) {
        this.amountOfUse = amountOfUse;
    }
}
