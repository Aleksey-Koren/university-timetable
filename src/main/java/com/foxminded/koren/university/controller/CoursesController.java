package com.foxminded.koren.university.controller;

;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
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
        List<Course> courses = null;
        try {
            courses = courseService.getAll();
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        model.addAttribute("courses", courses);
        LOG.trace("Retrieving all students: success");
        return "courses/index";
    }

    @GetMapping("/new")
    public String newCourse(@ModelAttribute("course") Course course) {
        LOG.trace("Request for form to create new course");
        return "courses/new";
    }

    @PostMapping("/new-create")
    public String create(@ModelAttribute("course") Course course) {
        LOG.trace("Creating new course name = {}", course.getName());
        try {
            courseService.createNew(course);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Creating new course : success. course id = {}", course.getId());
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        LOG.trace("Request for form to update course id = {}", id);
        model.addAttribute("course", courseService.getById(id));
        return "courses/edit";
    }

    @PostMapping("/{id}/edit-update")
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

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        LOG.trace("Delete course id = {}", id);
        try {
            courseService.deleteById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Delete course id = {} : success", id);
        return "redirect:/courses";
    }
 }