package com.github.justalexandeer.SocialNewsAppBackend.util;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.*;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("dataMapper")
public class DataMapper {

    public List<ResponseSimplePost> mapListPostToListSimplePost(List<Post> listOfPost) {
        List<ResponseSimplePost> listOfResponseSimplePost = new ArrayList<>();
        for(Post post: listOfPost) {
            listOfResponseSimplePost.add(
                    new ResponseSimplePost(
                            post.getId(),
                            post.getName(),
                            post.getDate(),
                            new ResponseAppUser(post.getAppUser().getName(), post.getAppUser().getUsername()),
                            post.getCategory(),
                            post.getTags(),
                            post.getContent(),
                            post.getCommentCount()
                    )
            );
        }
        return listOfResponseSimplePost;
    }

    public Page<ResponseSimplePost> mapPagePostToPageSimplePost(Page<Post> page) {
        return page.map(post -> {
            ResponseAppUser responseAppUser = new ResponseAppUser(
                    post.getAppUser().getName(),
                    post.getAppUser().getUsername()
            );
            ResponseSimplePost responseSimplePost = new ResponseSimplePost(
                    post.getId(),
                    post.getName(),
                    post.getDate(),
                    responseAppUser,
                    post.getCategory(),
                    post.getTags(),
                    post.getContent(),
                    post.getCommentCount()
            );
            return responseSimplePost;
        });
    }

    public ResponseFullPost mapToResponseFullPost(
            Post post,
            Page<Comment> pageOfComment,
            AnswerRepository answerRepository
    ) {
        Page<ResponseComment> pageOfResponseComment = mapFromPageCommentToPageResponseComment(pageOfComment, answerRepository);
        return new ResponseFullPost(
                post.getId(),
                post.getName(),
                post.getDate(),
                mapAppUserToResponseAppUser(post.getAppUser()),
                post.getCategory(),
                post.getTags(),
                post.getContent(),
                pageOfResponseComment,
                post.getCommentCount()
        );
    }

    public Page<ResponseComment> mapFromPageCommentToPageResponseComment (
            Page<Comment> pageOfComment,
            AnswerRepository answerRepository
    ) {
        return pageOfComment.map(comment -> {
            List<Answer> answerList = answerRepository.findAllByComment(comment);
            List<ResponseAnswer> responseAnswerList = new ArrayList<>();
            answerList.forEach(answer -> {
                responseAnswerList.add(new ResponseAnswer(
                        answer.getId(),
                        answer.getContent(),
                        comment.getId(),
                        mapAppUserToResponseAppUser(answer.getAppUser())
                ));
            });
            return new ResponseComment(
                    comment.getId(),
                    comment.getContent(),
                    comment.getPost().getId(),
                    mapAppUserToResponseAppUser(comment.getAppUser()),
                    responseAnswerList
            );
        });
    }

    private ResponseAppUser mapAppUserToResponseAppUser(AppUser appUser) {
        return new ResponseAppUser(
                appUser.getName(),
                appUser.getUsername()
        );
    }
}
