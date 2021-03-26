package com.codesquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException(Model model, UserNotFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException(IllegalUserAccessException e) {
        return "redirect:/users";
    }

    @ExceptionHandler(UnauthorizedUserAccessException.class)
    private String handleUnauthorizedUserAccessException(Model model, UnauthorizedUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }
}
