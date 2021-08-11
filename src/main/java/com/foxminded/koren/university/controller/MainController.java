package com.foxminded.koren.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController extends BaseController {
    @GetMapping("/")
    public String mainPage() {
        return "main/main";
    }
}