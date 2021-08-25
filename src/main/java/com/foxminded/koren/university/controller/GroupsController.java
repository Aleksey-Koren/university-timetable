package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.dto.GroupDTO;
import com.foxminded.koren.university.controller.dto.GroupFormDTO;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.GroupService;
import com.foxminded.koren.university.service.StudentService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        try {
            GroupDTO dto = new GroupDTO.Builder()
                    .group(groupService.getById(id))
                    .students(studentService.getByGroupId(id))
                    .years()
                    .build();
            model.addAttribute("dto", dto);
            model.addAttribute("formDTO", new GroupFormDTO(dto.getGroup().getName(),
                    dto.getGroup().getYear()));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return "groups/edit";
    }
}