package ru.otus.spring.service.impl;

import ru.otus.spring.dao.RowDao;
import ru.otus.spring.service.RowService;

import java.util.stream.Collectors;

public class RowServiceImpl implements RowService {

    private final RowDao rowDao;

    public RowServiceImpl(RowDao rowDao) {
        this.rowDao = rowDao;
    }

    @Override
    public String getQuestionsAndAnswers() {
        var rows = rowDao.readAllRows();

        return rows.stream()
                .map(row -> String.format("q: %s? || a: %s", row.getQuestion(), row.getAnswer()))
                .collect(Collectors.joining("\n"));
    }
}
