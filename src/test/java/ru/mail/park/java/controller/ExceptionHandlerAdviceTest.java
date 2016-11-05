package ru.mail.park.java.controller;

import org.junit.Test;
import ru.mail.park.exceptions.ErrorMsg;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerAdviceTest extends AbstractControllerTest{
	
	@Test
	public void testNotFoundCode() throws Exception{
		mockMvc.perform(get("/shouldbenosuchaddress"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("msg").value(ErrorMsg.SERVER_ERROR_MSG));
	}

}
