package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.processors.GenreFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private OutputProcessor outputProcessor;

    @MockBean
    private InputProcessor inputProcessor;

    @MockBean
    private GenreFormatProcessor genreFormatProcessor;

    @Test
    void getGenreListFromInput() {
        List<GenreDto> expectedList = List.of(
                GenreDto.builder().name("genre1").build(),
                GenreDto.builder().name("genre2").build(),
                GenreDto.builder().name("genre3").build()
        );

        when(inputProcessor.readString())
                .thenReturn("genre1")
                .thenReturn("genre2")
                .thenReturn("genre3")
                .thenReturn("end");

        List<GenreDto> actualList = genreService.getGenreListFromInput();

        assertThat(actualList).hasSameElementsAs(expectedList);
    }

    @Test
    void updateGenreListFromInput() {
        List<GenreDto> genreDtoList = List.of(GenreDto.builder().build());

        List<GenreDto> expectedList = List.of(
                GenreDto.builder().name("genre1").build(),
                GenreDto.builder().name("genre2").build(),
                GenreDto.builder().name("genre3").build()
        );

        when(inputProcessor.readString())
                .thenReturn("genre1")
                .thenReturn("genre2")
                .thenReturn("genre3")
                .thenReturn("end");

        List<GenreDto> actualList = genreService.updateGenreListFromInput(genreDtoList);

        assertThat(actualList).hasSameElementsAs(expectedList);
    }
}