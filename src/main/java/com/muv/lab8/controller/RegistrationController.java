package com.muv.lab8.controller;

import com.muv.lab8.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String email
    ) {
        if (userService.register(name, password, email)) {
            return "index";
        }
        return "registration";
    }

}
