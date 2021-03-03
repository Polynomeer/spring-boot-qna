package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @PostMapping("/users/create")
    public String create(User user) {
        System.out.println(user);
        users.add(user);
        return "redirect:/users";
    }
}