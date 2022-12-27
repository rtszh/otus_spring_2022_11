package ru.otus.spring.processors.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.processors.CsvProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class CsvProcessorImpl implements CsvProcessor {

    @Override
    public <T> List<T> parseCsv(Resource resource, Class<T> clazz) {

        InputStream inputStream;
        Reader reader;

        try {
            inputStream = resource.getInputStream();
            reader = new InputStreamReader(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + resource.getFilename());
        }

        return new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withSkipLines(1)
                .build()
                .parse();
    }
}
