package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.AudienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/audiences")
public class Audiences extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(Audiences.class);

    private AudienceService audienceService;

    @Autowired
    public Audiences(AudienceService audienceService){
        this.audienceService = audienceService;
    }

    @GetMapping()
    public String index(Model model) {
        LOG.trace("CONTROLLER: Retrieving all audiences");
        List<Audience> audiences = audienceService.getAll();
        if (audiences.isEmpty()) {
            throw new NoEntitiesInDatabaseException("There is no any audiences in database");
        }
        model.addAttribute("audiences", audiences);
        LOG.trace("CONTROLLER: Retrieving all audiences: success");
        return "audiences/index";
    }
}