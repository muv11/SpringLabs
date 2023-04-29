package com.muv.lab8.controller;

import com.muv.lab8.entity.Ticket;
import com.muv.lab8.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public String getTickets(Model model) {
        List<Ticket> list = ticketService.getAllTickets();
        model.addAttribute("ticketsList", list);
        return "tickets";
    }

    @GetMapping("/addTicket")
    public String getAddTicket() {
        return "add_ticket";
    }

    @PostMapping("/addTicket")
    public String postAddTicket(
            @RequestParam String customerName,
            @RequestParam int place,
            @RequestParam int price,
            @RequestParam String filmName,
            Model model
    ) {
        ticketService.saveTicket(new Ticket(customerName, place, price), filmName);
        return getTickets(model);
    }

    @GetMapping("/updateTicket/{id}")
    public String getUpdateTicket(
            @PathVariable Long id,
            Model model
    ) {
        Ticket ticket = ticketService.getCurrentTicket(id);
        model.addAttribute("id", id);
        model.addAttribute("customerName", ticket.getCustomerName());
        model.addAttribute("place", ticket.getPlace());
        model.addAttribute("price", ticket.getPrice());
        model.addAttribute("filmName", ticket.getSessionId().getFilmName());
        return "update_ticket";
    }

    @PostMapping("/updateTicket/{id}")
    public String postUpdateTicket(
            @PathVariable Long id,
            @RequestParam String customerName,
            @RequestParam int place,
            @RequestParam int price,
            @RequestParam String filmName,
            Model model
    ) {
        ticketService.saveTicket(new Ticket(id, customerName, place, price), filmName);
        return getTickets(model);
    }

    @GetMapping("/deleteTicket/{id}")
    public String getDeleteTicket(
            @PathVariable Long id,
            Model model
    ) {
        return postDeleteTicket(id, model);
    }

    @PostMapping("/deleteTicket")
    public String postDeleteTicket(
            Long id,
            Model model
    ) {
        ticketService.deleteSession(id);
        return getTickets(model);
    }

}
