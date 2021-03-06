package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.dto.GroupGetDTO;
import com.foxminded.koren.university.controller.dto.GroupPostDTO;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.GroupService;
import com.foxminded.koren.university.service.StudentService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(GroupsController.class);

    private GroupService groupService;
    private StudentService studentService;

    @Autowired
    public GroupsController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.trace("Retrieving all groups");
        List<Group> groups = null;
        try {
            groups = groupService.getAll();
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        model.addAttribute("groups", groups);
        LOG.trace("Retrieving all groups : success");
        return "groups/index";
    }

    @GetMapping("/new")
    public String newGroup (Model model) {
        LOG.trace("Getting form to new Group");
        try {
            model.addAttribute("dto", new GroupGetDTO.Builder().years().build());
            model.addAttribute("formDTO", new GroupPostDTO());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "groups/new";
    }

    @PostMapping("/new-create")
    public String create(@ModelAttribute("formDTO") GroupPostDTO dto) {
        LOG.trace("Creating new group");
        try {
            Group newGroup = groupService.createNew(new Group(dto.getGroupName(), dto.getYear()));
            return String.format("redirect:/groups/%s/edit", newGroup.getId());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(),e);
        }
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        LOG.trace("Getting form to edit group id = {}", id);
        try {
            GroupGetDTO dto = new GroupGetDTO.Builder()
                    .group(groupService.getById(id))
                    .students(studentService.getByGroupId(id))
                    .years()
                    .build();
            model.addAttribute("dto", dto);
            model.addAttribute("formDTO", new GroupPostDTO(dto.getGroup().getName(),
                    dto.getGroup().getYear()));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Getting form to edit group id = {} : success", id);
        return "groups/edit";
    }

    @PostMapping("/{id}/edit-update")
    public String update(@ModelAttribute("formDTO") GroupPostDTO dto, @PathVariable("id") int id) {
        try {
            groupService.update(new Group(dto.getGroupId(), dto.getGroupName(), dto.getYear()));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit-select")
    public String selectStudents(Model model, @PathVariable("id") int id) {
        LOG.trace("Getting form to add student(s) to group id = {}", id);
        try {
            model.addAttribute("dto", new GroupGetDTO.Builder()
                    .group(groupService.getById(id))
                    .studentsWithoutGroup(studentService.getAllWithoutGroup())
                    .build());

            model.addAttribute("formDTO", new GroupPostDTO.Builder()
                    .groupId(id)
                    .studentsIds(new ArrayList<>())
                    .build());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Getting form to add student(s) to group id = {} : success", id);
        return "groups/edit-select";
    }

    @PostMapping("/{id}/edit-add")
    public String addStudents(@ModelAttribute("formDTO") GroupPostDTO dto, @PathVariable("id") int id) {
        LOG.trace("Add student(s) to group id = {} student(s) ID = {}", id, dto.getStudentsIds());
        try {
            dto.getStudentsIds().forEach(s -> studentService.addStudentToGroup(s, id));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Add student(s) to group id = {} student(s) ID = {} : success", id, dto.getStudentId());
        return String.format("redirect:/groups/%s/edit", id);
    }

    @PostMapping("/{id}/edit-remove")
    public String removeStudent(@ModelAttribute GroupPostDTO dto, @PathVariable("id") int id) {
        LOG.trace("Remove student id = {} from group id = {}", dto.getStudentId(), id);
        studentService.removeStudentFromGroup(dto.getStudentId());
        LOG.trace("Remove student id = {} from group id = {} : success", dto.getStudentId(), id);
        return String.format("redirect:/groups/%s/edit", id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        LOG.trace("Deleting group id = {}", id);
        try {
            groupService.deleteById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Deleting group id = {} : success", id);
        return "redirect:/groups";
    }
}