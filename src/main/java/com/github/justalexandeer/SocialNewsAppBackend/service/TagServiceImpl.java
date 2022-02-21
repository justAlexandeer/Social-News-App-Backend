package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseTag;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.TagRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final DataMapper dataMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, PostRepository postRepository, DataMapper dataMapper) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.dataMapper = dataMapper;
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
    public Response<List<ResponseTag>> getTopTags(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Tag> listOfTopTags = tagRepository.findAllByOrderByAmountOfUseDesc(pageable);
        List<ResponseTag> listOfResponseTag = dataMapper.mapListTagToListResponseTag(listOfTopTags);
        return new Response<List<ResponseTag>>("success", null, listOfResponseTag);
    }

    @Override
    public void setTagToPost(String postId, String tagId) {
        Post post = postRepository.getById(Long.valueOf(postId));
        Tag tag = tagRepository.getById(Long.valueOf(tagId));
        tag.setAmountOfUse(tag.getAmountOfUse() + 1);
        post.getTags().add(tag);
    }

    @Override
    public void setTagsToPost(String postId, String tagsId) {
        List<String> listOfTagsId = new ArrayList(Arrays.asList(tagsId.split(",")));
        Post post = postRepository.getById(Long.valueOf(postId));
        for(String tagId: listOfTagsId) {
            Tag tag = tagRepository.getById(Long.valueOf(tagId));
            tag.setAmountOfUse(tag.getAmountOfUse() + 1);
            post.getTags().add(tag);
        }
    }

    @Override
    public void createTag(String tagName) {
        Tag tag = new Tag(null, tagName, 0);
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
