package com.foxminded.koren.university.controller;

;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.service.CourseService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Integer id, Model model) {
        LOG.trace("Retrieving course by id = {}", id);
        model.addAttribute("audience", courseService.getById(id));
        LOG.trace("Retrieving course by id = {} : success", id);
        return "courses/getById";
    }

    @GetMapping("/new")
    public String newAudience(@ModelAttribute("course") Course audience) {
        LOG.trace("Request for form to create new course");
        return "courses/new";
    }

    @PostMapping
    public String create(@ModelAttribute("course") Course course) {
        LOG.trace("Creating new course");
        try {
            courseService.createNew(course);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Creating new course : success");
        return "/courses/createSuccess";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        LOG.trace("Request for form to update course id = {}", id);
        model.addAttribute("course", courseService.getById(id));
        return "courses/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("course") Course course) {
        LOG.trace("Updating course id = {}", course.getId());
        try {
            courseService.update(course);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Updating course id = {} : success", course.getId());
        return "redirect:/courses";
    }
}