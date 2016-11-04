package ru.mail.park.java.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.mail.park.Application;

/**
 * Created by kirrok on 04.11.16.
 */
@SpringBootApplication
@Import(Application.class)
public class JavaHerokuMysqlTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
