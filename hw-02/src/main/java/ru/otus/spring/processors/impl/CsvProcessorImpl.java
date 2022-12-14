package ru.otus.spring.processors.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.processors.CsvProcessor;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class CsvProcessorImpl implements CsvProcessor {

    @Override
    public <T> List<T> parseCsvFile(Resource resource, Class<T> clazz) {

        FileReader fileReader;

        try {
            fileReader = new FileReader(resource.getFile());
        } catch (IOException e) {
            throw new RuntimeException("Файл не найден: " + resource.getFilename());
        }

        return new CsvToBeanBuilder(fileReader)
                .withType(clazz)
                .withSkipLines(1)
                .build()
                .parse();
    }
}
