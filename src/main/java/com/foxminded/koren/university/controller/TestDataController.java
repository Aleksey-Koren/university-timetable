package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.service.TestDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestDataController {

    private final Logger LOG = LoggerFactory.getLogger(TestDataController.class);

    @Autowired
    TestDataService testDataService;

    @GetMapping("/reload-test-data")
    public String reloadTestData() {
        LOG.trace("Try to reload test data");
        testDataService.reloadTestData();
        LOG.trace("Reload test data : success");
        return "main/main";
    }
}