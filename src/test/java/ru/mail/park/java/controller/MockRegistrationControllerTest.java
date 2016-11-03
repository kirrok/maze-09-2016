package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mail.park.services.impl.AccountServiceImpl;

/**
 * Created by kirrok on 04.10.16.
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public class MockRegistrationControllerTest {
    @MockBean
    private AccountServiceImpl accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
//        mockMvc.perform(post("/api/registration")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"login\":\"kirrok\", \"password\":\"1234\", \"email\":\"some@email.ru\"}")
//        ).andExpect(status().isOk()).andExpect(jsonPath("login").value("kirrok"));
    }
}
