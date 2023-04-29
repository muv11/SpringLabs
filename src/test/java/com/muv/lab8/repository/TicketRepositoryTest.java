package com.muv.lab8.repository;

import com.muv.lab8.Lab8Application;
import com.muv.lab8.entity.Ticket;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Lab8Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketRepositoryTest {

    @Autowired
    private CinemaRepository<Ticket> ticketRepository;
    @Autowired
    private SessionRepositoryImpl sessionRepository;
    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeAll
    public void init() {
        ticket1 = new Ticket("Customer #1", 50, 500, sessionRepository.findByFilmName("Film #1"));
        ticket2 = new Ticket("Customer @@@@", 16, 1000, sessionRepository.findByFilmName("Film #1"));
    }

    @Order(1)
    @Test
    public void save() {
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        Assertions.assertEquals(ticket1.getCustomerName(), ticketRepository.findById(ticket1.getId()).getCustomerName());
    }

    @Order(2)
    @Test
    public void findById() {
        Assertions.assertEquals(ticket2.getPlace(), ticketRepository.findById(ticket2.getId()).getPlace());
    }

    @Order(3)
    @Test
    public void findAll() {
        Assertions.assertTrue(ticketRepository.findAll().size() > 2);
    }

    @Order(4)
    @Test
    public void update() {
        ticket2.setCustomerName("Customer #2");
        ticketRepository.save(ticket2);
        Assertions.assertEquals(ticket2.getCustomerName(), ticketRepository.findById(ticket2.getId()).getCustomerName());
    }

    @Order(5)
    @Test
    public void deleteById() {
        ticketRepository.deleteById(ticket1.getId());
        ticketRepository.deleteById(ticket2.getId());
        Assertions.assertNull(ticketRepository.findById(ticket1.getId()));
        Assertions.assertNull(ticketRepository.findById(ticket2.getId()));
    }
}