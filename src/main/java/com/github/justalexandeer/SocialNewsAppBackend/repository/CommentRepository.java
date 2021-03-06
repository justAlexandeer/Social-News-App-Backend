package com.github.justalexandeer.SocialNewsAppBackend.repository;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
