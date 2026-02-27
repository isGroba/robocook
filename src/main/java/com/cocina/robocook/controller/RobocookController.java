package com.cocina.robocook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backoffice")
public class RobocookController {

    @GetMapping("/list")
    public String main(Model model){
        model.addAttribute("title", "Robocook");
        return "robocook";
    }

}
