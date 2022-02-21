package com.github.justalexandeer.SocialNewsAppBackend.domain.entity;

import javafx.geometry.Pos;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @ManyToMany()
    @Column(name = "roles")
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "appUser")
    @Column(name = "posts")
    private Collection<Post> posts = new ArrayList<>();
    public AppUser() {

    }

    public AppUser(String name, String username, String password, Collection<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

}
