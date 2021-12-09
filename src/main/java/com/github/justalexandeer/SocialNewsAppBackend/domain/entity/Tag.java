package com.github.justalexandeer.SocialNewsAppBackend.domain.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    public Tag() {

    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
