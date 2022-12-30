package ru.otus.spring.factories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

public class AuthorDtoFactory {
    public static AuthorDto createAuthorDto(Author author) {
        return AuthorDto.builder()
                .name(author.getName())
                .build();
    }
}
