package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);

        logger.error("Sessioned User : {}, Writer : {}", sessionedUser.getUserId(), question.getWriter());

        if (!sessionedUser.isMatchedUserId(question.getWriter())) {
            throw new IllegalUserAccessException();
        }

        model.addAttribute(question);

        return "/qna/updateForm";
    }

    @PutMapping("/{id}/form")
    public String update(@PathVariable("id") long id, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);

        if (!sessionedUser.isMatchedUserId(question.getWriter())) {
            throw new IllegalUserAccessException();
        }

        questionService.update(question, updatedQuestion);

        return "redirect:/questions";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/qna/list";
    }

    @PostMapping()
    public String question(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        logger.error("before saved");
        questionService.save(question, session);
        logger.error("question saved");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);

        if (!sessionedUser.isMatchedUserId(question.getWriter())) {
            throw new IllegalUserAccessException();
        }

        questionService.delete(question);

        return "redirect:/questions";
    }
}
