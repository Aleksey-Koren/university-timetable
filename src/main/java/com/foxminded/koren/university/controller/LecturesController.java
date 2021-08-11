package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/lectures")
public class LecturesController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LecturesController.class);

    private LectureService lectureService;

    @Autowired
    public LecturesController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.trace("Retrieving all lectures");
        List<Lecture> lectures = lectureService.getAll();
        if (lectures.isEmpty()) {
            throw new NoEntitiesInDatabaseException("There is no any lectures in database");
        }
        model.addAttribute("lectures", lectures);
        LOG.trace("Retrieving all lectures : success");
        return "lectures/index";
    }
}