package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Comment;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.CommentRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnswerServiceImpl(
            AnswerRepository answerRepository,
            CommentRepository commentRepository,
            UserRepository userRepository
    ) {
        this.answerRepository = answerRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createAnswer(String appUserName, String answerContent, String commentId) {
        AppUser appUser = userRepository.findByUsername(appUserName);
        Comment comment = commentRepository.getById(Long.valueOf(commentId));
        Answer answer = new Answer(null, answerContent, comment, appUser);
        answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(String answerId) {
        return answerRepository.getById(Long.valueOf(answerId));
    }

    @Override
    public void changeAnswer(String answerId, String answerContent) {
        Answer answer = answerRepository.getById(Long.valueOf(answerId));
        answer.setContent(answerContent);
    }

    @Override
    public void deleteAnswer(String answerId) {
        answerRepository.deleteById(Long.valueOf(answerId));
    }

}
