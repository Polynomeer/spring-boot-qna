package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.UnauthorizedUserAccessException;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public AnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable Long id, String contents, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            throw new UnauthorizedUserAccessException();
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);
        Answer answer = new Answer(user, question, contents);

        answerService.save(answer);
        model.addAttribute("answers", answerService.findAll());

        return "/qna/show";
    }
}
