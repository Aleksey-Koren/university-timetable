package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.AudienceService;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/audiences")
public class AudiencesController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AudiencesController.class);

    private AudienceService audienceService;

    @Autowired
    public AudiencesController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.trace("Retrieving all audiences");
        List<Audience> audiences = audienceService.getAll();
        if (audiences.isEmpty()) {
            throw new NoEntitiesInDatabaseException("There is no any audiences in database");
        }
        model.addAttribute("audiences", audiences);
        LOG.trace("Retrieving all audiences: success");
        return "audiences/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Integer id, Model model) {
        LOG.trace("Retrieving audience by id = {}", id);
        model.addAttribute("audience", audienceService.getById(id));
        LOG.trace("Retrieving audience by id = {} : success", id);
        return "audiences/getById";
    }

    @GetMapping("/new")
    public String newAudience(@ModelAttribute("audience") Audience audience) {
        LOG.trace("Request for form to create new Audience");
        return "audiences/new";
    }

    @PostMapping
    public String create(@ModelAttribute("audience") Audience audience) {
        LOG.trace("Creating new audience");
        try {
            audienceService.createNew(audience);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Creating new audience : success");
        return "/audiences/createSuccess";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        LOG.trace("Request for form to update audience id = {}", id);
        model.addAttribute("audience", audienceService.getById(id));
        return "audiences/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("audience") Audience audience) {
        LOG.trace("Updating audience id = {}", audience.getId());
        try {
            audienceService.update(audience);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Updating audience id = {} : success", audience.getId());
        return "redirect:/audiences";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        LOG.trace("Deleting audience id = {}", id);
        audienceService.deleteById(id);
        LOG.trace("Deleting audience id = {} : success", id);
        return "redirect:/audiences";
    }
}