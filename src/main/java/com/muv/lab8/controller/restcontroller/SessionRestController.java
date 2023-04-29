package com.muv.lab8.controller.restcontroller;

import com.muv.lab8.entity.Session;
import com.muv.lab8.repository.CinemaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {

    private final CinemaRepository<Session> sessionCinemaRepository;

    public SessionRestController(CinemaRepository<Session> sessionCinemaRepository) {
        this.sessionCinemaRepository = sessionCinemaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSession(@PathVariable Long id) {
        if (sessionCinemaRepository.findById(id) != null) {
            return new ResponseEntity<>(sessionCinemaRepository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionCinemaRepository.findAll();
    }

    @PostMapping("/{filmName}/{dateTime}")
    public ResponseEntity<Session> saveSession(
            @PathVariable String filmName,
            @PathVariable String dateTime
    ) {
        Session session = new Session(filmName, dateTime);
        sessionCinemaRepository.save(session);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{filmName}/{dateTime}")
    public ResponseEntity<Session> updateSession(
            @PathVariable Long id,
            @PathVariable String filmName,
            @PathVariable String dateTime
    ) {
        Session session = sessionCinemaRepository.findById(id);
        session.setFilmName(filmName);
        session.setDateTime(dateTime);
        sessionCinemaRepository.save(session);
        return new ResponseEntity<>(sessionCinemaRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Session> deleteSession(@PathVariable Long id) {
        sessionCinemaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
