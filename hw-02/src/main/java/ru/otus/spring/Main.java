package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.service.ApplicationRunner;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Main.class);

        var applicationRunner = ctx.getBean(ApplicationRunner.class);

        applicationRunner.run();
    }
}
