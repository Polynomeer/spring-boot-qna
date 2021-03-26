package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.Result;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.exception.UnauthorizedUserAccessException;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public ApiAnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UnauthorizedUserAccessException();
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(questionId);
        Answer answer = new Answer(user, question, contents);

        return answerService.save(answer);
    }

    @DeleteMapping("{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UnauthorizedUserAccessException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerService.findAnswerById(answerId);

        if (!sessionedUser.isMatchedUserId(answer.getUserId())) {
            throw new IllegalUserAccessException();
        }
        answerService.delete(answer);
        return Result.ok();
    }
}
