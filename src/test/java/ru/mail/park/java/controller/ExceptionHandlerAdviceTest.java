package ru.mail.park.java.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerAdviceTest extends AbstractControllerTest{
	
	@Test
	public void testNotFoundCode() throws Exception{
		mockMvc.perform(get("/shouldbenosuchaddress"))
		.andExpect(status().isNotFound());
	}

}
