package com.foxminded.koren.university.controller.exception_handlers;

import com.foxminded.koren.university.controller.exceptions.BusinessLogicException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public String businessLogicException(BusinessLogicException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "exception";
    }
}