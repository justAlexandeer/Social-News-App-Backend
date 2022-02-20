package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getTopTags(int limit) {
        return new ArrayList<Tag>();
    }

    @Override
    public void setTagToPost(String postId, String tagId) {
        Post post = postRepository.getById(Long.valueOf(postId));
        Tag tag = tagRepository.getById(Long.valueOf(tagId));
        post.getTags().add(tag);
    }

    @Override
    public void setTagsToPost(String postId, String tagsId) {
        List<String> listOfTagsId = new ArrayList(Arrays.asList(tagsId.split(",")));
        Post post = postRepository.getById(Long.valueOf(postId));
        for(String tagId: listOfTagsId) {
            Tag tag = tagRepository.getById(Long.valueOf(tagId));
            post.getTags().add(tag);
        }
    }

    @Override
    public void createTag(String tagName) {
        Tag tag = new Tag(null, tagName);
        tagRepository.save(tag);
    }

    @Override
    public void changeTag(String tagId, String tagName) {
        Tag tag = tagRepository.getById(Long.valueOf(tagId));
        tag.setName(tagName);
    }

    @Override
    public void deleteTag(String tagId) {
        Tag tag = tagRepository.getById(Long.valueOf(tagId));
        ArrayList<Tag> listOfTags = new ArrayList<>();
        listOfTags.add(tag);
        List<Post> listOfPosts = postRepository.findAllByTagsIn(listOfTags);
        listOfPosts.forEach(post -> {
            System.out.println(post.getId());
            post.getTags().removeAll(listOfTags);
        });
        tagRepository.deleteById(Long.valueOf(tagId));
    }

}
