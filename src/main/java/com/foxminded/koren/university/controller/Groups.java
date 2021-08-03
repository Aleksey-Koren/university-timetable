package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.BusinessLogicException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class Groups {

    private static final Logger LOG = LoggerFactory.getLogger(Groups.class);

    private GroupService groupService;

    @Autowired
    public Groups(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.info("Retrieving all groups");
        List<Group> groups = groupService.getAll();
        if (groups.isEmpty()) {
            throw new BusinessLogicException("There is no any groups in database");
        }
        model.addAttribute("groups", groups);
        LOG.info("Retrieving all groups : success");
        return "groups/index";
    }
}