package com.muv.lab8.controller.restcontroller;

import com.muv.lab8.entity.User;
import com.muv.lab8.repository.UserRepository;
import com.muv.lab8.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UsersRestController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        if (userRepository.findById(id) != null) {
            return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        if (userRepository.findByName(name) != null) {
            return new ResponseEntity<>(userRepository.findByName(name), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/{name}/{password}/{email}")
    public ResponseEntity<User> registerUser(
            @PathVariable String name,
            @PathVariable String password,
            @PathVariable String email
    ) {
        if (userService.register(name, password, email)) {
            return new ResponseEntity<>(userRepository.findByName(name), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/{name}/{password}/{email}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @PathVariable String name,
            @PathVariable String password,
            @PathVariable String email
    ) {
        User user = userRepository.findById(id);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
