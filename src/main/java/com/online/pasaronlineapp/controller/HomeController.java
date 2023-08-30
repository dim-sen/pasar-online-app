package com.online.pasaronlineapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = "/index")
    public String home(Model model, Principal principal) {
        model.addAttribute("title", "Home");
        if (principal == null) {
            return "redirect:/login";
        }
        return "index";
    }
}
