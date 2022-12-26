package ru.otus.spring.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
@ConfigurationProperties(prefix = "application")
@Data
public class AppProperties {

    private String testQnAPath;
    private String testQnAExtension;
    private Locale locale;

    public Resource getTestFilePath() {
        return new ClassPathResource(
                String.format("%s_%s.%s", testQnAPath, locale, testQnAExtension)
        );
    }
}
