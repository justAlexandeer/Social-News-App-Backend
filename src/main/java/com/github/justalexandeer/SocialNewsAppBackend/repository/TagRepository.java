package com.github.justalexandeer.SocialNewsAppBackend.repository;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
