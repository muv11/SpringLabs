package com.muv.lab8.repository;

import com.muv.lab8.entity.Ticket;
import com.muv.lab8.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepositoryImpl implements CinemaRepository<Ticket> {

    private final EntityManager entityManager;

    public TicketRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Ticket ticket) {
        entityManager.getTransaction().begin();
        if (ticket.getId() == null) {
            entityManager.persist(ticket);
        }
        if (ticket.getId() != null) {
            entityManager.merge(ticket);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Ticket findById(Long id) {
        Query query = entityManager.createQuery("SELECT ticket FROM Ticket ticket WHERE id = :id AND deleted = false", User.class);
        try {
            return (Ticket) query.setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Ticket ticket = findById(id);
        ticket.setDeleted(true);
        save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE deleted = false", Ticket.class).getResultList();
    }
}
