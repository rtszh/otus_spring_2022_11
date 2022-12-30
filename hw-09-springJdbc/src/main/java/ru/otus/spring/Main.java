package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

//        PersonDao dao = context.getBean(PersonDao.class);
//
//        System.out.println("All count " + dao.count());
//
//        dao.insert(new Person(2, "ivan"));
//
//        System.out.println("All count " + dao.count());
//
//        Person ivan = dao.getById(2);
//
//        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());
//
//        System.out.println(dao.getAll());

        Console.main(args);
    }
}
