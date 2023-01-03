package ru.otus.spring.service;

import ru.otus.spring.dto.AuthorDto;

import java.util.List;


public interface AuthorService {
    List<AuthorDto> getAuthorListFromInput();

    List<AuthorDto> updateAuthorListFromInput(List<AuthorDto> authorDtoList);
}
