package ru.mail.park.java.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.mail.park.Application;
import ru.mail.park.WebSocketConfig;

/**
 * Created by kirrok on 04.11.16.
 */
@SpringBootApplication
@Import({Application.class, WebSocketConfig.class})

public class JavaHerokuMysqlTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{Application.class, WebSocketConfig.class}, args);
    }
}
