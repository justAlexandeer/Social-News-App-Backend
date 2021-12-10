package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addTagToPost(Long postId, Long tagId) {
        Post post = postRepository.getById(postId);
        Tag tag = tagRepository.getById(tagId);
        post.getTags().add(tag);
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }
}
