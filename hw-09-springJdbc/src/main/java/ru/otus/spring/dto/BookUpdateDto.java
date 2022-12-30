package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BookUpdateDto {
    String oldTitle;
    String updatedTitle;
    List<AuthorDto> updatedAuthorsDto;
    List<GenreDto> updatedGenresDto;
}
