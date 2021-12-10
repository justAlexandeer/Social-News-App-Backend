package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface TagService {
    void addTagToPost(Long postId, Long tagId);
    void saveTag(Tag tag);
}
