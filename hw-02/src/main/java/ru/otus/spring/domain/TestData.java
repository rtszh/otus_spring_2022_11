package ru.otus.spring.domain;

import lombok.Value;

import java.util.List;

@Value
public class TestData {
    List<Row> rows;

    public static TestData of(List<Row> rows) {
        return new TestData(rows);
    }
}
