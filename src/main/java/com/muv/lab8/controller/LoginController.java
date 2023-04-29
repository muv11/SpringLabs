package com.muv.lab8.controller;

import com.muv.lab8.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;
    private final SessionController sessionController;
    private final TicketController ticketController;

    public LoginController(UserService userService, SessionController sessionController, TicketController ticketController) {
        this.userService = userService;
        this.sessionController = sessionController;
        this.ticketController = ticketController;
    }

    @GetMapping("/")
    public String getLogin() {
        return "index";
    }

    @PostMapping("/")
    public String postLogin(
            @RequestParam String name,
            @RequestParam String password,
            Model model
    ) {
        if (userService.login(name, password)) {
            if (userService.isUserAdmin(name)) {
                return sessionController.getSessions(model);
            }
            return ticketController.getTickets(model);
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "index";
    }

}
