package ru.otus.spring.service;

import ru.otus.spring.dto.GenreDto;

import java.util.List;


public interface GenreService {

    List<GenreDto> getGenreListFromInput();

    List<GenreDto> updateGenreListFromInput(List<GenreDto> genreDtoList);

}
