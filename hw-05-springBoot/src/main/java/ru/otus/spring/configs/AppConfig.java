package ru.otus.spring.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.processors.impl.InputProcessorImpl;
import ru.otus.spring.processors.impl.OutputProcessorImpl;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    @Bean
    InputProcessor inputProcessor() {
        return new InputProcessorImpl(System.in);
    }

    @Bean
    OutputProcessor outputProcessor() {
        return new OutputProcessorImpl(System.out);
    }

}
