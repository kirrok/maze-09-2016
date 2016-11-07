package ru.mail.park.java.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import ru.mail.park.models.User;
import ru.mail.park.repositories.UserDAO;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.impl.AccountServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kirrok on 04.10.16.
 */

public class RegistrationControllerTest extends AbstractControllerTest {

    @Autowired
    UserDAO userDAO;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "testPassword";
    private static final Long ID = 1L;


    @Test
    public void testRegistration() throws Exception {
        final MockHttpSession mockHttpSession = new MockHttpSession();

        mockMvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty());

        Assert.notNull(mockHttpSession.getAttribute("id"));
    }

    @Test
    public void testLogin() throws Exception {
        final User user = new User(LOGIN, PASSWORD);

        final AccountService accountService = new AccountServiceImpl(userDAO, passwordEncoder);
        final long id = accountService.addUser(user);

        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"testLogin\",\"password\":\"testPassword\"}")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));

        Assert.notNull(mockHttpSession.getAttribute("id"));
    }

    @Test
    public void testSession() throws Exception {
        mockMvc.perform(get("/api/session")
                .sessionAttr("id", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ID));
    }

    @Test
    public void testLogoutOK() throws Exception {
        final MockHttpSession mockHttpSession = new MockHttpSession();

        mockMvc.perform(delete("/api/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(mockHttpSession)
                .sessionAttr("id", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ID));

        Assert.isNull(mockHttpSession.getAttribute("id"));
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

        final AccountService accountService = new AccountServiceImpl(userDAO, passwordEncoder);
        final long id = accountService.addUser(user);

        mockMvc.perform(get("/api/user/" + id)
                .sessionAttr("id", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("login").value(LOGIN))
                .andExpect(jsonPath("maxScore").exists())
                .andExpect(jsonPath("password").doesNotExist());
    }

    @Test
    public void testUpdateUser() throws Exception {
        final User user = new User(LOGIN, passwordEncoder.encode(PASSWORD));

        final AccountService accountService = new AccountServiceImpl(userDAO, passwordEncoder);
        final long id = accountService.addUser(user);


        mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON).sessionAttr("id", id)
                .content("{\"login\":\"newLogin\",\"password\":\"newPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));

        final User updatedUser = new User("newLogin", "newPassword");
        updatedUser.setId(id);

        assertThat(accountService.getUserById(id)).isEqualTo(updatedUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        final User user = new User(LOGIN, PASSWORD);

        final AccountService accountService = new AccountServiceImpl(userDAO, passwordEncoder);
        final long id = accountService.addUser(user);

        mockMvc.perform(delete("/api/user")
                .sessionAttr("id", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));

        assertThat(accountService.getUserById(id)).isNull();
    }
}
