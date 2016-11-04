package ru.mail.park.java.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;

public class ExceptionHandlerAdviceTest extends AbstractControllerTest{
	
	@Test
	public void testNotFound() throws Exception{
		mockMvc.perform(get("/shouldbenosuchaddress"))
		.andExpect(status().isNotFound());
	}

}
