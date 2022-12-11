package ru.otus.spring.dao.impl;

import ru.otus.spring.configs.AppConfig;
import ru.otus.spring.dao.RowDao;
import ru.otus.spring.domain.Row;
import ru.otus.spring.processors.CsvProcessor;

import java.util.List;

public class RowDaoImpl implements RowDao {

    private final AppConfig appConfig;
    private final CsvProcessor csvProcessor;

    public RowDaoImpl(AppConfig appConfig, CsvProcessor csvProcessor) {
        this.appConfig = appConfig;
        this.csvProcessor = csvProcessor;
    }

    @Override
    public List<Row> readAllRows() {
        return csvProcessor.parseCsvFile(appConfig.getTestQnAPath(), Row.class);
    }
}
