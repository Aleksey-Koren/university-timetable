package com.foxminded.koren.university.controller;

;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CoursesController.class);

    private CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public String index(Model model) {
        LOG.trace("Retrieving all courses");
        List<Course> courses = courseService.getAll();
        if (courses.isEmpty()) {
            throw new NoEntitiesInDatabaseException("There is no any courses in database");
        }
        model.addAttribute("courses", courses);
        LOG.trace("Retrieving all students: success");
        return "courses/index";
    }
}