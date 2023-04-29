package com.muv.lab8.controller.restcontroller;

import com.muv.lab8.entity.Ticket;
import com.muv.lab8.repository.CinemaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    private final CinemaRepository<Ticket> ticketCinemaRepository;

    public TicketRestController(CinemaRepository<Ticket> ticketCinemaRepository) {
        this.ticketCinemaRepository = ticketCinemaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        if (ticketCinemaRepository.findById(id) != null) {
            return new ResponseEntity<>(ticketCinemaRepository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketCinemaRepository.findAll();
    }

    @PostMapping("/new")
    public ResponseEntity<Ticket> saveTicket(
            @RequestBody Ticket ticket
    ) {
        ticketCinemaRepository.save(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @PutMapping("/new/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable Long id,
            @RequestBody Ticket ticket
    ) {
        ticket.setId(id);
        ticketCinemaRepository.save(ticket);
        return new ResponseEntity<>(ticketCinemaRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable Long id) {
        ticketCinemaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
