package com.muv.lab8.controller.restcontroller;

import com.muv.lab8.entity.Role;
import com.muv.lab8.entity.Session;
import com.muv.lab8.entity.User;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UsersRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<User> jacksonTester;
    private final Long id = 40L; //upd
    private final User user = new User(id, "createduser", "strongpass", "mail@mail.ru", new Role(1L, "ROLE_USER"));

    @Order(1)
    @Test
    void registerUser() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/users/createduser/strongpass/mail@mail.ru").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(user).getJson());
    }

    @Order(2)
    @Test
    void getUserById() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/byId/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(user).getJson());
    }

    @Order(3)
    @Test
    void getUserByName() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/byName/" + user.getName()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(user).getJson());
    }

    @Order(4)
    @Test
    void getAllUsers() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertTrue(response.getContentAsString().length() > 0);
    }

    @Order(5)
    @Test
    void updateUser() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/users/" + id + "/updateduser/strongpassword/gmail@gmail.com").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        user.setName("updateduser");
        user.setPassword("strongpassword");
        user.setEmail("gmail@gmail.com");
        Assertions.assertEquals(response.getContentAsString(),
                jacksonTester.write(user).getJson());
    }

    @Order(6)
    @Test
    void deleteUser() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/users/" + id).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}