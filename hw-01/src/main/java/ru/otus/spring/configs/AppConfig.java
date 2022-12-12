package ru.otus.spring.configs;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class AppConfig {
    private final String testQnAPath;

    public AppConfig(String testQnAPath) {
        this.testQnAPath = testQnAPath;
    }

    public Resource getTestQnAPath() {
        return new ClassPathResource(testQnAPath);
    }
}
