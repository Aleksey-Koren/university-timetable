package com.foxminded.koren.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage() {
        return "main/main";
    }

//    @GetMapping("/calculator")
//    public String calculate(@RequestParam(value = "a", required = false) String a,
//                            @RequestParam(value = "b", required = false) String b,
//                            @RequestParam(value = "action", required = false) String action,
//                            Model model) {
//        if (action.equals("mult")) {
//            int result = Integer.parseInt(a) + Integer.parseInt(b);
//            model.addAttribute("result", "" + result);
//            return "main/calculator";
//        }
//        return null;
//    }

}