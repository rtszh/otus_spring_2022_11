package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

import java.util.List;


public interface GenreDao {
    List<Genre> getAll();

    Genre getById(Long id);

    Genre insert(GenreDto genreDto);
}
