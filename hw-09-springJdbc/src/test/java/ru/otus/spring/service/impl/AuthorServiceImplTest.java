package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.processors.AuthorFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;
    @MockBean
    private OutputProcessor outputProcessor;
    @MockBean
    private InputProcessor inputProcessor;
    @MockBean
    private AuthorFormatProcessor authorFormatProcessor;

    @Test
    void getAuthorListFromInput() {
        List<AuthorDto> expectedList = List.of(
                AuthorDto.builder().name("author1").build(),
                AuthorDto.builder().name("author2").build(),
                AuthorDto.builder().name("author3").build()
        );

        when(inputProcessor.readString())
                .thenReturn("author1")
                .thenReturn("author2")
                .thenReturn("author3")
                .thenReturn("end");

        List<AuthorDto> actualList = authorService.getAuthorListFromInput();

        assertThat(actualList).hasSameElementsAs(expectedList);
    }

    @Test
    void updateAuthorListFromInput() {
        List<AuthorDto> authorDtoList = List.of(AuthorDto.builder().build());
        List<AuthorDto> expectedList = List.of(
                AuthorDto.builder().name("author1").build(),
                AuthorDto.builder().name("author2").build(),
                AuthorDto.builder().name("author3").build()
        );

        when(inputProcessor.readString())
                .thenReturn("author1")
                .thenReturn("author2")
                .thenReturn("author3")
                .thenReturn("end");

        List<AuthorDto> actualList = authorService.updateAuthorListFromInput(authorDtoList);

        assertThat(actualList).hasSameElementsAs(expectedList);

    }
}