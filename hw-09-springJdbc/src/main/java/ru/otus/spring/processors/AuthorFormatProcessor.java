package ru.otus.spring.processors;

import ru.otus.spring.dto.AuthorDto;

import java.util.List;

public interface AuthorFormatProcessor {
    String formatAuthorList(List<AuthorDto> authorDto);
}
