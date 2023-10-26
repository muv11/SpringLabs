package com.muv.lab8.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muv.lab8.entity.Ticket;
import com.muv.lab8.repository.SessionRepositoryImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TicketRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Ticket> jacksonTester;
    @Autowired
    private SessionRepositoryImpl sessionRepository;
    private Long id;
    private Ticket ticket;

    private static String asJsonString(Ticket ticket) {
        try {
            return new ObjectMapper().writeValueAsString(ticket);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public void init() {
        ticket = new Ticket("Customer 1", 20, 500, sessionRepository.findByFilmName("Film #1"));
    }

    @Order(1)
    @Test
    void saveTicket() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/tickets/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticket)))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(ticket).getJson());
    }

    @Order(2)
    @Test
    void getTicketWhenExists() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/tickets/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(ticket).getJson());
    }

    @Order(3)
    @Test
    void getTicketWhenDoesntExist() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/tickets/1000").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Order(4)
    @Test
    void getAllTickets() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/tickets").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertTrue(response.getContentAsString().length() > 0);
    }

    @Order(5)
    @Test
    void updateTicket() throws Exception {
        ticket.setCustomerName("Customer 777");
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/tickets/new/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticket)))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(ticket).getJson());
    }

    @Order(6)
    @Test
    void deleteTicket() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/tickets/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}