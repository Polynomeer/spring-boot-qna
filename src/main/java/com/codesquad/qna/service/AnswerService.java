package com.codesquad.qna.service;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Answer answer) {
        answer.delete();
        answerRepository.save(answer);
    }

}
