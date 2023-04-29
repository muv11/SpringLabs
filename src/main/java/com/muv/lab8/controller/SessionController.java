package com.muv.lab8.controller;

import com.muv.lab8.entity.Session;
import com.muv.lab8.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public String getSessions(Model model) {
        List<Session> list = sessionService.findAllSessions();
        model.addAttribute("sessionsList", list);
        return "cinema_sessions";
    }

    @GetMapping("/addSession")
    public String getAddSession() {
        return "add_session";
    }

    @PostMapping("/addSession")
    public String postAddSession(
            @RequestParam String filmName,
            @RequestParam String dateTime,
            Model model
    ) {
        sessionService.saveSession(new Session(filmName, dateTime));
        return getSessions(model);
    }

    @GetMapping("/updateSession/{id}")
    public String updateSession(
            @PathVariable Long id,
            Model model
    ) {
        Session session = sessionService.getCurrentSession(id);
        model.addAttribute("id", id);
        model.addAttribute("filmName", session.getFilmName());
        model.addAttribute("dateTime", session.getDateTime());
        return "update_session";
    }

    @PostMapping("/updateSession/{id}")
    public String updateSession(
            @PathVariable Long id,
            @RequestParam String filmName,
            @RequestParam String dateTime,
            Model model
    ) {
        sessionService.saveSession(new Session(id, filmName, dateTime));
        return getSessions(model);
    }

    @GetMapping("/deleteSession/{id}")
    public String getDeleteSession(
            @PathVariable Long id,
            Model model
    ) {
        return postDeleteSession(id, model);
    }

    @PostMapping("/deleteSession")
    public String postDeleteSession(
            Long id,
            Model model
    ) {
        sessionService.deleteSession(id);
        return getSessions(model);
    }

}
