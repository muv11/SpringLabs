package com.muv.lab8.service;

import com.muv.lab8.entity.Session;
import com.muv.lab8.entity.Ticket;
import com.muv.lab8.repository.CinemaRepository;
import com.muv.lab8.repository.SessionRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final CinemaRepository<Ticket> ticketRepository;
    private final SessionRepositoryImpl sessionRepository;

    public TicketService(SessionRepositoryImpl sessionRepository, CinemaRepository<Ticket> ticketRepository) {
        this.sessionRepository = sessionRepository;
        this.ticketRepository = ticketRepository;
    }

    public void saveTicket(Ticket ticket, String filmName) {
        Session session = sessionRepository.findByFilmName(filmName);
        ticket.setSessionId(session);
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void deleteSession(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket getCurrentTicket(Long id) {
        return ticketRepository.findById(id);
    }
}
