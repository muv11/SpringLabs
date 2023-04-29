package com.muv.lab8.repository;

import com.muv.lab8.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        }
        if (user.getId() != null) {
            entityManager.merge(user);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public User findByName(String name) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE name = :name", User.class);
        try {
            return (User) query.setParameter("name", name).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE id = :id", User.class);
        try {
            return (User) query.setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

}
