package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

    @Autowired
    GenreDaoImpl genreDao;

    @Test
    void getAll() {
        List<Genre> expectedGenreList = List.of(
                Genre.builder().id(1L).name("genre1").build()
        );

        var actualGenreList = genreDao.getAll();

        assertThat(actualGenreList).hasSameElementsAs(expectedGenreList);
    }

    @Test
    void getById() {
        Genre expectedGenre = Genre.builder().id(1L).name("genre1").build();

        var actualGenre = genreDao.getById(1L);

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    void insert() {
        String insertedGenreName = "insertedGenre";
        GenreDto genreDto = GenreDto.builder().name(insertedGenreName).build();

        genreDao.insert(genreDto);

        List<Genre> allGenres = genreDao.getAll();

        List<Genre> filteredGenres = allGenres.stream()
                .filter(genre -> genre.getName().equalsIgnoreCase(insertedGenreName))
                .collect(Collectors.toList());

        assertThat(filteredGenres.size()).isEqualTo(1);
        assertThat(filteredGenres.get(0).getName()).isEqualTo(insertedGenreName);
    }
}