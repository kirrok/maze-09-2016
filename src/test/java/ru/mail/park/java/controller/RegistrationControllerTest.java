package ru.mail.park.java.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.util.Assert;
import ru.mail.park.models.User;
import ru.mail.park.repositories.UserDAO;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.impl.AccountServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kirrok on 04.10.16.
 */

public class RegistrationControllerTest extends AbstractControllerTest {

    @Autowired
    UserDAO userDAO;

    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "testPassword";
    private static final Long ID = 2L;

    @Test
    public void testRegistration() throws Exception {
        final MockHttpSession mockHttpSession = new MockHttpSession();

        mockMvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}")
                .session(mockHttpSession))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(status().isOk());

        Assert.notNull(mockHttpSession.getAttribute("id"));
    }

    @Test
    public void testLogin() throws Exception {
        final User user = new User(LOGIN, PASSWORD);

        final AccountService accountService = new AccountServiceImpl(userDAO);
        accountService.addUser(user);

        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}")
                .session(mockHttpSession))
                .andExpect(jsonPath("id").value(1))
                .andExpect(status().isOk());

        Assert.notNull(mockHttpSession.getAttribute("id"));
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

        final AccountService accountService = new AccountServiceImpl(userDAO);
        accountService.addUser(user);

        mockMvc.perform(get("/api/user/2")
                .sessionAttr("id", ID)
                .param("id", "2"))
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("login").value(LOGIN))
                .andExpect(jsonPath("maxScore").exists())
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
