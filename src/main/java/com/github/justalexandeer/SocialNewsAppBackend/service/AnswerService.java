package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Answer;

public interface AnswerService {
    void createAnswer(String appUserName, String answerContent, String commentId);

    Answer getAnswerById(String answerId);

    void changeAnswer(String answerId, String answerContent);

    void deleteAnswer(String answerId);
}
