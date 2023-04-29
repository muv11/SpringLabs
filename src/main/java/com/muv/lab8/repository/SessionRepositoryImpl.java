package com.muv.lab8.repository;

import com.muv.lab8.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SessionRepositoryImpl implements CinemaRepository<Session> {

    private final EntityManager entityManager;

    public SessionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Session session) {
        entityManager.getTransaction().begin();
        if (session.getId() == null) {
            entityManager.persist(session);
        }
        if (session.getId() != null) {
            entityManager.merge(session);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Session findById(Long id) {
        Query query = entityManager.createQuery("SELECT session FROM Session session WHERE id = :id AND deleted = false", Session.class);
        try {
            return (Session) query.setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Session findByFilmName(String filmName) {
        Query query = entityManager.createQuery("SELECT session FROM Session session WHERE filmName = :filmName AND deleted = false", Session.class);
        try {
            return (Session) query.setParameter("filmName", filmName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Session session = findById(id);
        session.setDeleted(true);
        save(session);
    }

    @Override
    public List<Session> findAll() {
        return entityManager.createQuery("SELECT s FROM Session s WHERE deleted = false", Session.class).getResultList();
    }
}
