package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.configs.AppProperties;
import ru.otus.spring.configs.TestConfig;
import ru.otus.spring.domain.Row;
import ru.otus.spring.processors.CsvProcessor;
import ru.otus.spring.processors.impl.CsvProcessorImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RowDaoImplTestData {

    private CsvProcessor csvProcessor;
    private AppProperties properties;

    @BeforeEach
    void setUp() {
        var ctx = new AnnotationConfigApplicationContext(TestConfig.class);
        csvProcessor = new CsvProcessorImpl();
        properties = ctx.getBean(AppProperties.class);
    }

    @Test
    void readAllRows() {
        var resource = properties.getTestQnAPath();
        var rowListFromFile = csvProcessor.parseCsv(resource, Row.class);

        var correctRowList = List.of(
                new Row("Question1", "Answer1"),
                new Row("Question2", "Answer2")
        );

        assertEquals(rowListFromFile, correctRowList);
    }
}