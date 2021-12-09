package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;
import com.github.justalexandeer.SocialNewsAppBackend.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void addAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}
