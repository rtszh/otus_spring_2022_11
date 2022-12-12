package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.RowService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");

        RowService rowService = ctx.getBean(RowService.class);

        System.out.println(rowService.getQuestionsAndAnswers());
    }
}
