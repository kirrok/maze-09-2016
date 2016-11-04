package ru.mail.park.java.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import ru.mail.park.models.User;
import ru.mail.park.services.impl.AccountServiceImpl;

/**
 * Created by kirrok on 04.10.16.
 */

public class RegistrationControllerTest extends AbstractControllerTest {

    @MockBean
    AccountServiceImpl accountService;

    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "testPassword";
    private static final Long ID = 2L;
    private static final int MAX_SCORE = 121;


    @Test
    public void testRegistration() throws Exception {
        when(accountService.getUserByLogin(LOGIN)).thenReturn(null);
        final User user = new User(LOGIN, PASSWORD);
        when(accountService.addUser(user)).thenReturn(ID);

        mockMvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}"))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        final User user = new User(LOGIN, PASSWORD);
        user.setId(ID);

        when(accountService.getUserByLogin(LOGIN)).thenReturn(user);
        when(accountService.addUser(user)).thenReturn(ID);

        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}")
                .session(mockHttpSession))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }

    @Test
    public void testSession() throws Exception {
        mockMvc.perform(get("/api/session")
                .sessionAttr("id", ID))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogoutOK() throws Exception {
        mockMvc.perform(delete("/api/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("id", ID))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogoutNotAuthorized() throws Exception {
        mockMvc.perform(delete("/api/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetUser() throws Exception {
        final User user = new User(LOGIN, PASSWORD);
        user.setId(ID);
        user.setMaxScore(MAX_SCORE);

        when(accountService.getUserById(2)).thenReturn(user);

        mockMvc.perform(get("/api/user/2")
                .sessionAttr("id", ID)
                .param("id", "2"))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(jsonPath("login").value(LOGIN))
                .andExpect(jsonPath("maxScore").value(MAX_SCORE))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON).sessionAttr("id", ID)
                .content("{\"login\":\"newLogin\",\"password\":\"newPassword\"}"))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user")
                .sessionAttr("id", ID))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(status().isOk());
    }
}
