package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Row;
import ru.otus.spring.processors.CsvProcessor;
import ru.otus.spring.processors.impl.CsvProcessorImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
class RowDaoImplTestData {

    private CsvProcessor csvProcessor = new CsvProcessorImpl();
    private String path = "unitTesting_testQnA.csv";


    @Test
    void readAllRows() {
        var resource = new ClassPathResource(path);
        var rowListFromFile = csvProcessor.parseCsv(resource, Row.class);

        var correctRowList = List.of(
                new Row("Question1", "Answer1"),
                new Row("Question2", "Answer2")
        );

        assertEquals(rowListFromFile, correctRowList);
    }
}