package com.muv.lab8.repository;

import com.muv.lab8.Lab8Application;
import com.muv.lab8.entity.Role;
import com.muv.lab8.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Lab8Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    private final User user = new User("somenewuser", "somepassword", "some@mail.ru", new Role(1L, "ROLE_USER"));

    @BeforeAll
    void save() {
        userRepository.save(user);
        user.setId(userRepository.findByName(user.getName()).getId());
        user.setRole(userRepository.findByName(user.getName()).getRole());
        Assertions.assertEquals(user, userRepository.findByName(user.getName()));
    }

    @Order(1)
    @Test
    void findByName() {
        Assertions.assertEquals(user, userRepository.findByName(user.getName()));
    }

    @Order(2)
    @Test
    void findById() {
        Assertions.assertEquals(user, userRepository.findById(user.getId()));
    }

    @Order(3)
    @Test
    void findAll() {
        Assertions.assertTrue(userRepository.findAll().size() >= 2);
    }

    @Order(4)
    @Test
    void deleteById() {
        userRepository.deleteById(user.getId());
        Assertions.assertNull(userRepository.findById(user.getId()));
    }
}