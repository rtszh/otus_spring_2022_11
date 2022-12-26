package ru.otus.spring.dao;

import ru.otus.spring.domain.Row;

import java.util.List;

public interface RowDao {
    List<Row> readAllRows();
}
