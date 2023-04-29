package com.muv.lab8.repository;

import com.muv.lab8.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    void save(User user);
    User findByName(String name);
    User findById(Long id);
    void deleteById(Long id);
    List<User> findAll();
}
