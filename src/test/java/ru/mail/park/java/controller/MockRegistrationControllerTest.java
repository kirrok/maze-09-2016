package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kirrok on 04.10.16.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MockRegistrationControllerTest {
    public MockHttpSession mockHttpSession;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"login\",\"password\":\"test\"}")
        ).andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        mockHttpSession = new MockHttpSession();
        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"login\",\"password\":\"test\"}")
                .session(mockHttpSession)
        ).andExpect(status().isOk());
    }
}
