package ru.otus.spring.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class AppProperties {

    @Value("${testQnAPath}")
    private String testQnAPath;

    public Resource getTestQnAPath() {
        return new ClassPathResource(testQnAPath);
    }
}
