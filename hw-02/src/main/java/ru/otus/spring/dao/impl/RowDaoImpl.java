package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.configs.AppProperties;
import ru.otus.spring.dao.RowDao;
import ru.otus.spring.domain.Row;
import ru.otus.spring.processors.CsvProcessor;

import java.util.List;

@Component
public class RowDaoImpl implements RowDao {

    private final AppProperties appProperties;
    private final CsvProcessor csvProcessor;

    public RowDaoImpl(AppProperties appProperties, CsvProcessor csvProcessor) {
        this.appProperties = appProperties;
        this.csvProcessor = csvProcessor;
    }

    @Override
    public List<Row> readAllRows() {
        return csvProcessor.parseCsv(appProperties.getTestQnAPath(), Row.class);
    }
}
