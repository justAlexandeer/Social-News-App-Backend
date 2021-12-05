package com.github.justalexandeer.SocialNewsAppBackend.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    public Role() {

    }

    public Role(String name) {
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
