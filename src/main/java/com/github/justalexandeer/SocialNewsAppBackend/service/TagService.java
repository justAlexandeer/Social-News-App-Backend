package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag);
    List<Tag> findAllTags();
    void setTagToPost(String postId, String tagId);
    void setTagsToPost(String postId, String tagsId);
    void createTag(String tagName);
    void changeTag(String tagId, String tagName);
    void deleteTag(String tagId);
}
