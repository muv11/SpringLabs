package com.muv.lab8.repository;

import com.muv.lab8.Lab8Application;
import com.muv.lab8.entity.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Lab8Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionRepositoryTest {

    @Autowired
    private CinemaRepository<Session> sessionRepository;
    private final Session session1 = new Session("Fantasy Film #1", "15-09-25");
    private final Session session2 = new Session("Horror Film #2", "20-08-23");

    @BeforeAll
    void save() {
        sessionRepository.save(session1);
        sessionRepository.save(session2);
        Assertions.assertEquals(session1.getFilmName(), sessionRepository.findById(session1.getId()).getFilmName());
    }

    @Order(1)
    @Test
    void findById() {
        Assertions.assertEquals(session2.getDateTime(), sessionRepository.findById(session2.getId()).getDateTime());
    }

    @Order(2)
    @Test
    void findAll() {
        Assertions.assertTrue(sessionRepository.findAll().size() >= 2);
    }

    @Order(3)
    @Test
    void update() {
        session1.setFilmName("FF #111");
        sessionRepository.save(session1);
        Assertions.assertEquals(session1.getFilmName(), sessionRepository.findById(session1.getId()).getFilmName());
    }

    @Order(4)
    @Test
    void deleteById() {
        sessionRepository.deleteById(session1.getId());
        sessionRepository.deleteById(session2.getId());
        Assertions.assertNull(sessionRepository.findById(session1.getId()));
        Assertions.assertNull(sessionRepository.findById(session2.getId()));
    }
}