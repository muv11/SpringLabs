package com.muv.lab8.controller.restcontroller;

import com.muv.lab8.entity.Session;
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
class SessionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Session> jacksonTester;
    private final Long id = 13L; //upd
    private final Session session = new Session(id,"Film x1", "15.08 16:00");

    @Order(1)
    @Test
    void saveSession() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/sessions/Film x1/15.08 16:00").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(session).getJson());
    }

    @Order(2)
    @Test
    void getSessionWhenExists() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/sessions/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(session).getJson());
    }

    @Order(3)
    @Test
    void getSessionWhenDoesntExist() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/sessions/1000").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Order(4)
    @Test
    void getAllSessions() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/sessions").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertTrue(response.getContentAsString().length() > 0);
    }

    @Order(5)
    @Test
    void updateSession() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/sessions/" + id + "/Film x111/20.04 18:00").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        session.setFilmName("Film x111");
        session.setDateTime("20.04 18:00");
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(session).getJson());
    }

    @Order(6)
    @Test
    void deleteSession() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/sessions/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}