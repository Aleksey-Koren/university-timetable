package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.BusinessLogicException;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
public class Students {

    private static final Logger LOG = LoggerFactory.getLogger(Students.class);

    private  StudentService studentService;

    @Autowired
    public Students(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public String all(Model model) {
        LOG.info("Retrieving all students");
        List<Student> students = studentService.getAll();
        if(students.isEmpty()){
            throw new BusinessLogicException("There is no any students in database");
        }
        model.addAttribute("students", students);
        LOG.info("Retrieving all students: success");
        return "students/index";
    }
}