package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.BusinessLogicException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.AudienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/audiences")
public class Audiences {

    private static final Logger LOG = LoggerFactory.getLogger(Audiences.class);

    private AudienceService audienceService;

    @Autowired
    public Audiences(AudienceService audienceService){
        this.audienceService = audienceService;
    }

    @GetMapping()
    public String index(Model model) {
        LOG.info("Retrieving all audiences");
        List<Audience> audiences = audienceService.getAll();
        if (audiences.isEmpty()) {
            throw new BusinessLogicException("There is no any audiences in database");
        }
        model.addAttribute("audiences", audiences);
        LOG.info("Retrieving all audiences: success");
        return "audiences/index";
    }

//    @ExceptionHandler(BusinessLogicException.class)
//    public String businessLogicException(BusinessLogicException e, Model model) {
//        model.addAttribute("message", e.getMessage());
//        return "exception";
//    }
}