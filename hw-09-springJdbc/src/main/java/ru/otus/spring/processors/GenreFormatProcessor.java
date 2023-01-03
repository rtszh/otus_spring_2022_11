package ru.otus.spring.processors;

import ru.otus.spring.dto.GenreDto;

import java.util.List;

public interface GenreFormatProcessor {
    String formatGenreList(List<GenreDto> genreDtoList);
}
