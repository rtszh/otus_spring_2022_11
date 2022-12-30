package ru.otus.spring.factories;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

public class GenreDtoFactory {
    public static GenreDto createGenreDto(Genre genre) {
        return GenreDto.builder()
                .name(genre.getName())
                .build();
    }
}
