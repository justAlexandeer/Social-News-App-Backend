package com.github.justalexandeer.SocialNewsAppBackend.repository;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query("SELECT a FROM AppUser a ORDER BY a.posts.size DESC")
    List<AppUser> findTopAuthors(Pageable pageable);
}
