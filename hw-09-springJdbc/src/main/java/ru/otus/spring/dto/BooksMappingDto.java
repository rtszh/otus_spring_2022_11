package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Value;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.List;


@Value
@Builder
public class BooksMappingDto {
    List<BookRawDto> bookRawDtoList;
    List<Author> authors;
    List<Genre> genres;
}
