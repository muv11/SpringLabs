package com.muv.lab8.service;

import com.muv.lab8.entity.Role;
import com.muv.lab8.entity.User;
import com.muv.lab8.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String name, String password, String email) {
        User user = new User(name, password, email);
        user.setRole(new Role(1L, "ROLE_USER"));
        if (userRepository.findByName(name) == null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean login(String name, String password) {
        User user = userRepository.findByName(name);
        return user.getName().equals(name) && user.getPassword().equals(password);
    }

    public boolean isUserAdmin(String name) {
        User user = userRepository.findByName(name);
        return user.getRole().getId() == 2L;
    }
}
