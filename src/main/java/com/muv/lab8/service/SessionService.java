package com.muv.lab8.service;

import com.muv.lab8.entity.Session;
import com.muv.lab8.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final CinemaRepository<Session> sessionCinemaRepository;

    public SessionService(CinemaRepository<Session> sessionCinemaRepository) {
        this.sessionCinemaRepository = sessionCinemaRepository;
    }

    public Session getCurrentSession(Long id) {
        return sessionCinemaRepository.findById(id);
    }

    public List<Session> findAllSessions() {
        return sessionCinemaRepository.findAll();
    }

    public void saveSession(Session session) {
        sessionCinemaRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionCinemaRepository.deleteById(id);
    }

}
