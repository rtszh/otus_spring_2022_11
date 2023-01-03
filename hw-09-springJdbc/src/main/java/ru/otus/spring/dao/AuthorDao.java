package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

import java.util.List;


public interface AuthorDao {
    List<Author> getAll();

    Author getById(Long id);

    Author insert(AuthorDto authorDto);
}
