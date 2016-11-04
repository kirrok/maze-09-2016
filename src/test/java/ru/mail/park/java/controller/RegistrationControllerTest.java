package ru.mail.park.java.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

/**
 * Created by kirrok on 04.10.16.
 */

public class RegistrationControllerTest extends AbstractControllerTest {
	@Test
	public void testRegistration() throws Exception {
		mockMvc.perform(post("/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"login\":\"login\",\"password\":\"test\"}")).andExpect(status().isOk());
	}

	@Test
	public void testLogin() throws Exception {
		testRegistration();
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"login\":\"login\",\"password\":\"test\"}")
				.session(mockHttpSession)).andExpect(status().isOk());
	}
}
