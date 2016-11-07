package ru.mail.park.java.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerAdviceTest extends AbstractControllerTest{
	
	@Test
	public void testNotFoundCode() throws Exception {
		mockMvc.perform(get("/shouldbenosuchaddress"))
				.andExpect(status().isNotFound());
		/*
			Почему то, когда запускаем приложение через тестовый класс исключение не выбрасывается

			Пробовал добавить в application.properties

			spring.mvc.throw-exception-if-no-handler-found=true
			spring.resources.add-mappings=false

			не помогло :(
		 */

	}
}
