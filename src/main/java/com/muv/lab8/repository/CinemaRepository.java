package com.muv.lab8.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository<T> {
    void save(T object);
    T findById(Long id);
    void deleteById(Long id);
    List<T> findAll();

}
