package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class BaseController {

    @ExceptionHandler(NoEntitiesInDatabaseException.class)
    private String NoEntitiesInDatabaseException(NoEntitiesInDatabaseException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "exception";
    }
}