package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.BusinessLogicException;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class Teachers {

    private static final Logger LOG = LoggerFactory.getLogger(Teachers.class);

    private TeacherService teacherService;

    @Autowired
    public Teachers(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.info("Retrieving all teachers");
        List<Teacher> teachers = teacherService.getAll();
        if (teachers.isEmpty()) {
            throw new BusinessLogicException("There is no any teachers in database");
        }
        model.addAttribute("teachers", teachers);
        LOG.info("Retrieving all teachers : success");
        return "teachers/index";
    }

//    @ExceptionHandler(BusinessLogicException.class)
//    public String businessLogicException(BusinessLogicException e, Model model) {
//        model.addAttribute("message", e.getMessage());
//        return "exception";
//    }
}