package ru.otus.spring.dao.impl.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.dao.impl.AuthorDaoImpl;
import ru.otus.spring.dao.impl.BookDaoImpl;
import ru.otus.spring.dao.impl.GenreDaoImpl;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.processors.BookMappingProcessor;
import ru.otus.spring.processors.impl.BookMappingProcessorImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({AuthorDaoImpl.class, GenreDaoImpl.class, BookDaoImpl.class, BookMappingProcessorImpl.class})
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Autowired
    private BookMappingProcessor bookMappingProcessor;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Test
    void insert() {

        BookDto bookDto = BookDto.builder()
                .title("title100")
                .authorsDto(List.of(AuthorDto.builder().name("author100").build()))
                .genresDto(List.of(GenreDto.builder().name("genre100").build()))
                .build();


        var books1 = bookDao.getAll();

        bookDao.insert(bookDto);

        var books2 = bookDao.getAll();

        List<Book> actualDifferenceList = new ArrayList<>(books2);
        actualDifferenceList.removeAll(books1);

        assertThat(actualDifferenceList.size()).isEqualTo(1);
        assertThat(actualDifferenceList.get(0).getTitle()).isEqualTo(bookDto.getTitle());

    }

    @Test
    void getAll() {
        var actualBookList = bookDao.getAll();

        List<String> expectedBookListTitles = List.of("title1", "title2");
        List<String> expectedBookListAuthorsNames = List.of("author1", "author2");
        List<String> expectedBookListGenresNames = List.of("genre1");

        var actualBookListTitles = actualBookList.stream()
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());

        var actualBookListAuthorsNames = actualBookList.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .map(Author::getName)
                .distinct()
                .collect(Collectors.toList());

        var actualBookListGenresNames = actualBookList.stream()
                .map(Book::getGenres)
                .flatMap(Collection::stream)
                .map(Genre::getName)
                .distinct()
                .collect(Collectors.toList());

        assertThat(actualBookListTitles).hasSameElementsAs(expectedBookListTitles);
        assertThat(actualBookListAuthorsNames).hasSameElementsAs(expectedBookListAuthorsNames);
        assertThat(actualBookListGenresNames).hasSameElementsAs(expectedBookListGenresNames);

    }

    @Test
    void getByTitle() {
        String title = "title1";

        List<String> expectedBookListAuthorsNames = List.of("author1");
        List<String> expectedBookListGenresNames = List.of("genre1");

        var actualBook = bookDao.getByTitle(title);

        var actualBookListAuthorsNames = actualBook.getAuthors().stream()
                .map(Author::getName)
                .distinct()
                .collect(Collectors.toList());

        var actualBookListGenresNames = actualBook.getGenres().stream()
                .map(Genre::getName)
                .distinct()
                .collect(Collectors.toList());

        assertThat(title).isEqualTo(actualBook.getTitle());
        assertThat(actualBookListAuthorsNames).hasSameElementsAs(expectedBookListAuthorsNames);
        assertThat(actualBookListGenresNames).hasSameElementsAs(expectedBookListGenresNames);

    }

    @Test
    void deleteByTitle() {
        String title = "title1";

        var actualBookListBeforeDelete = bookDao.getAll();
        var actualBookListTitlesBeforeDelete = actualBookListBeforeDelete.stream()
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());

        assertThat(actualBookListTitlesBeforeDelete).contains(title);

        bookDao.deleteByTitle(title);

        var actualBookListAfterDelete = bookDao.getAll();
        var actualBookListTitlesAfterDelete = actualBookListAfterDelete.stream()
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());

        assertThat(actualBookListTitlesAfterDelete).doesNotContain(title);
    }

    @Test
    void update() {

        String oldTitle = "title1";
        String updatedTitle = "title101";


        BookUpdateDto bookUpdateDto = createBookUpdateDto(oldTitle, updatedTitle);

        var bookListBeforeUpdate = bookDao.getAll();

        var bookListBeforeUpdateTitles = bookListBeforeUpdate.stream()
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());

        assertThat(bookListBeforeUpdateTitles)
                .contains(oldTitle)
                .doesNotContain(updatedTitle);

        bookDao.update(bookUpdateDto);

        var bookListAfterUpdate = bookDao.getAll();

        var bookListAfterUpdateTitles = bookListAfterUpdate.stream()
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());

        assertThat(bookListAfterUpdateTitles)
                .contains(updatedTitle)
                .doesNotContain(oldTitle);

    }

    private BookUpdateDto createBookUpdateDto(String oldTitle, String updatedTitle) {

        List<AuthorDto> updatedAuthorsDto = List.of(
                AuthorDto.builder().name("author100").build(),
                AuthorDto.builder().name("author101").build()
        );

        List<GenreDto> updatedGenresDto = List.of(
                GenreDto.builder().name("genre100").build(),
                GenreDto.builder().name("genre101").build()
        );


        return BookUpdateDto.builder()
                .oldTitle(oldTitle)
                .updatedTitle(updatedTitle)
                .updatedAuthorsDto(updatedAuthorsDto)
                .updatedGenresDto(updatedGenresDto)
                .build();
    }
}