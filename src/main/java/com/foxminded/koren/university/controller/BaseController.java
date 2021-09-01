package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

//    @ExceptionHandler(ControllerException.class)
//    private String controllerException(ControllerException e, Model model) {
//        LOG.trace(e.getClass().getSimpleName() + " in " + e.getStackTrace()[0] + " : because "+ e.getMessage());
//        model.addAttribute("message", e.getMessage());
//        return "exception";
//    }
}