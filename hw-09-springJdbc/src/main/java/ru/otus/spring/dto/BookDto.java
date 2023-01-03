package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BookDto {
    String title;
    List<AuthorDto> authorsDto;
    List<GenreDto> genresDto;
}
