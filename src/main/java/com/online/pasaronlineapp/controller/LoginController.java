package com.online.pasaronlineapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        model.addAttribute("tite", "Login");
        return "login";
    }

    @RequestMapping(value = "/index")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "index";
    }
}
