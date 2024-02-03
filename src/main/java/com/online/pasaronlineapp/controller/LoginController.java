package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dto.AdminDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import com.online.pasaronlineapp.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

//    @PostMapping(value = "/login")
//    public String postLogin(@ModelAttribute UsernamePassword usernamePassword, Model model) {
//        if (usernamePassword != null) {
//            adminService.authenticate(usernamePassword);
//            return "redirect:/index";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "login";
//        }
//    }

//    @GetMapping("/logout")
//    public String logout() {
//        return "redirect:/login?logout";
//    }
}
