package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.domain.dto.UserDto;
import com.online.pasaronlineapp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping(value = "/register-new")
    public String registerNew(@Valid @ModelAttribute(name = "userDto") UserDto userDto,
                              BindingResult bindingResult,
                              Model model) {

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("userDto", userDto);
                return "register";
            }

            Optional<UserDao> optionalUserDao = userService.findByUsername(userDto.getPhoneNumber());
            if (optionalUserDao.isPresent()) {
                model.addAttribute("userDto", userDto);
                model.addAttribute("emailError", "Phone Number has been registered");
                return "register";
            }

            if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
                userService.createUser(userDto);
                model.addAttribute("userDto", userDto);
                model.addAttribute("success", "Register Successfully");
            } else {
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Passwords are not the same");
                return "register";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Server Error, please try again later");
        }

        return "register";
    }
}
