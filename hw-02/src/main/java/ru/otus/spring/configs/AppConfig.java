package ru.otus.spring.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.processors.IoProcessor;
import ru.otus.spring.processors.impl.IoProcessorImpl;

@Configuration
public class AppConfig {

    @Bean
    IoProcessor ioProcessor() {
        return new IoProcessorImpl(
                System.in, System.out
        );
    }

}
