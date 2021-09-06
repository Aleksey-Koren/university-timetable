package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.TeacherService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeachersController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TeachersController.class);

    private TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.trace("Retrieving all teachers");
        List<Teacher> teachers = teacherService.getAll();
        model.addAttribute("teachers", teachers);
        LOG.trace("Retrieving all teachers : success");
        return "teachers/index";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") Teacher teacher) {
        LOG.trace("Getting form for creating new Teacher");
        return "teachers/new";
    }

    @PostMapping("/new-create")
    public String create(@ModelAttribute("teacher") Teacher teacher) {
        LOG.trace("Creating new teacher lastName = {}, firstName = {}",
                teacher.getLastName(), teacher.getFirstName());
        try {
            teacherService.createNew(teacher);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Creating new teacher lastName = {}, firstName = {} : : success id = {}",
                teacher.getLastName(), teacher.getFirstName(), teacher.getId());
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        LOG.trace("Retrieving form for editing the teacher id = {}", id);
        try {
            model.addAttribute("teacher", teacherService.getById(id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "teachers/edit";
    }

    @PostMapping("/{id}/edit-update")
    public String update(@ModelAttribute("teacher") Teacher teacher, @PathVariable("id") int id) {
        LOG.trace("Updating teacher id = {}", id);
        try {
            teacherService.update(teacher);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Updating teacher id = {} : success", id);
        return "redirect:/teachers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        LOG.trace("Deleting teacher id = {}", id);
        try {
            teacherService.deleteById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Deleting teacher id = {} : success", id);
        return "redirect:/teachers";
    }
}