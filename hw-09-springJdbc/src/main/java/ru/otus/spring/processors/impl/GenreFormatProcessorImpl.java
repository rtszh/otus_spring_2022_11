package ru.otus.spring.processors.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.processors.GenreFormatProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreFormatProcessorImpl implements GenreFormatProcessor {
    @Override
    public String formatGenreList(List<GenreDto> genreDtoList) {
        return genreDtoList.stream()
                .map(GenreDto::getName)
                .collect(Collectors.joining(", "));
    }
}
