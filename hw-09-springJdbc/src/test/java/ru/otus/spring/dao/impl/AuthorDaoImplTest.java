package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    void getAll() {
        List<Author> expectedAuthorList = List.of(
                Author.builder().id(1L).name("author1").build(),
                Author.builder().id(2L).name("author2").build()
        );

        var actualAuthorList = authorDao.getAll();

        assertThat(actualAuthorList).hasSameElementsAs(expectedAuthorList);

    }

    @Test
    void getById() {
        Author expectedAuthor = Author.builder().id(1L).name("author1").build();

        var actualAuthor = authorDao.getById(1L);

        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    void insert() {
        String insertedAuthorName = "insertedAuthor";
        AuthorDto authorDto = AuthorDto.builder().name(insertedAuthorName).build();

        authorDao.insert(authorDto);

        List<Author> allAuthors = authorDao.getAll();

        List<Author> filteredAuthors = allAuthors.stream()
                .filter(author -> author.getName().equalsIgnoreCase(insertedAuthorName))
                .collect(Collectors.toList());

        assertThat(filteredAuthors.size()).isEqualTo(1);
        assertThat(filteredAuthors.get(0).getName()).isEqualTo(insertedAuthorName);
    }
}