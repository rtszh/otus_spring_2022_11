package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookRawDto {
    Long bookId;
    Long authorId;
    Long genreId;
    String title;
}
