package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import ru.otus.spring.service.ApplicationRunner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        var ctx = SpringApplication.run(Main.class, args);

//        var applicationRunner = ctx.getBean(ApplicationRunner.class);
//
//        applicationRunner.run();
    }
}
