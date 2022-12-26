package ru.otus.spring.processors;

import org.springframework.core.io.Resource;

import java.util.List;

public interface CsvProcessor {

    <T> List<T> parseCsv(Resource resource, Class<T> clazz);
}
