package com.codesquad.qna.controller;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.UserService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUserById(userId);

        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }

        logger.debug("User : {} Login Success!", user.getUserId());
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");

        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @PostMapping
    public String create(User user) {
        logger.info("User: {}", user);

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute(user);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        logger.debug("User : {} found", sessionedUser.getUserId());
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't modify other user's info!!");
        }

        User user = userService.findUserById(id);
        logger.debug("User : {} found", sessionedUser.getUserId());
        model.addAttribute(user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't modify other user's info!!");
        }

        User user = userService.findUserById(id);
        logger.debug("User : {}", (user));

        if (!user.matchPassword(updatedUser)) {
            logger.debug("Password : \"{}\" does not match \"{}\"", updatedUser.getPassword(), user.getPassword());
            return "redirect:/users";
        }
        userService.update(user, updatedUser);

        return "redirect:/users";
    }

}
