package com.factoryproduct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("")
    public String greeting(Model model) {
        return "index";
    }

    @GetMapping("errormessage")
    public String error(@RequestParam String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "error/error";
    }

//    @GetMapping("errormessage")
//    public String error(Model model) {
//        model.addAttribute("errorMessage", "errorMessage");
//        return "error/error";
//    }
}
