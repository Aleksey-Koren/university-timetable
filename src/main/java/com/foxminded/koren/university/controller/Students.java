package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
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
public class Students extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(Students.class);

    private  StudentService studentService;

    @Autowired
    public Students(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public String all(Model model) {
        LOG.trace("CONTROLLER: Retrieving all students");
        List<Student> students = studentService.getAll();
        if(students.isEmpty()){
            throw new NoEntitiesInDatabaseException("There is no any students in database");
        }
        model.addAttribute("students", students);
        LOG.trace("CONTROLLER: Retrieving all students: success");
        return "students/index";
    }
}