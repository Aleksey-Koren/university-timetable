package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.dto.StudentGetDTO;
import com.foxminded.koren.university.controller.dto.StudentPostDTO;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.service.GroupService;
import com.foxminded.koren.university.service.StudentService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentsController.class);

    private StudentService studentService;
    private GroupService groupService;


    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public StudentsController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model) {
        LOG.trace("Retrieving all students");
        model.addAttribute("students", studentService.getAll());
        LOG.trace("Retrieving all students: success");
        return "students/index";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        LOG.trace("Retrieving form to create new student");
        return "students/new";
    }

    @PostMapping("/new-create")
    public String create(@ModelAttribute("student") Student student) {
        LOG.trace("Creating new student lastName = {}, firstName = {}", student.getLastName(), student.getFirstName());
        try {
            studentService.createNew(student);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Creating new student lastName = {}, firstName = {} : success id = {}",
                student.getLastName(), student.getFirstName(), student.getId());
        return String.format("redirect:/students/%s/edit", student.getId());
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        LOG.trace("Retrieving form to edit student");
        Student student = studentService.getById(id);
        StudentPostDTO formDTO = new StudentPostDTO(student.getId(),
                student.getGroup() != null ? student.getGroup().getId() : null,
                student.getFirstName(),
                student.getLastName());
        model.addAttribute("student", student);
        model.addAttribute("formDTO", formDTO);
        return "students/edit";
    }

    @PostMapping("/{id}/edit-update")
    public String update(@ModelAttribute("formDTO") StudentPostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Updating student id = {}", formDTO.getStudentId());
        formDTO.setStudentId(id);
        Student student = createStudent(formDTO);
        try {
            studentService.update(student);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "redirect:/students";
    }

    private Student createStudent(StudentPostDTO formDTO) {
        Student result = new Student();
        result.setId(formDTO.getStudentId());
        result.setFirstName(formDTO.getFirstName());
        result.setLastName(formDTO.getLastName());
        result.setGroup(formDTO.getGroupId() != null ? new Group(formDTO.getGroupId()) : null);
        return result;
    }

    @GetMapping("/{id}/edit-select")
    public String selectGroup(Model model,
                              @ModelAttribute("formDTO") StudentPostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Retrieving form to select group for student id = {}", id);
        try {
            model.addAttribute("dto", new StudentGetDTO(studentService.getById(id), groupService.getAll()));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "students/edit-select";
    }

    @PostMapping("/{id}/edit-add")
    public String addStudentToGroup(@ModelAttribute("formDTO") StudentPostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Adding student id = {} to group id = {}", id, formDTO.getGroupId());
        try {
            studentService.addStudentToGroup(id, formDTO.getGroupId());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Adding student id = {} to group id = {} : success", id, formDTO.getGroupId());
        return String.format("redirect:/students/%s/edit", id);
    }

    @PostMapping("/{id}/edit-remove")
    public String removeStudentFromGroup(@ModelAttribute("formDTO") StudentPostDTO formDTO,
                                         @PathVariable("id") int id) {
        LOG.trace("Removing student id = {} from group id = {}", id, formDTO.getGroupId());
        try {
            studentService.removeStudentFromGroup(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Removing student id = {} from group id = {} : success", id, formDTO.getGroupId());
        return String.format("redirect:/students/%s/edit", id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        LOG.trace("Deleting student id = {}", id);
        try {
            studentService.deleteById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Deleting student id = {} : success", id);
        return "redirect:/students";
    }
}