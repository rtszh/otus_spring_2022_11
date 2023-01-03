package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.processors.BookFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookDao bookDao;
    @MockBean
    private BookFormatProcessor bookFormatProcessor;
    @MockBean
    private OutputProcessor outputProcessor;
    @MockBean
    private InputProcessor inputProcessor;

    @Test
    void getAllBooks() {

        List<Book> booksFromDao = createBookListData();
        List<BookDto> expectedBooksDto = createBookDtoListData();

        when(bookDao.getAll()).thenReturn(booksFromDao);

        List<BookDto> actualBooksDto = bookService.getAllBooks();

        assertThat(actualBooksDto).hasSameElementsAs(expectedBooksDto);
    }

    @Test
    void getBookByTitle() {
        String bookTitle = "title1";
        Book bookFromDao = createBookData();
        BookDto expectedBookDto = createBookDtoData();

        when(bookDao.getByTitle(bookTitle)).thenReturn(bookFromDao);

        BookDto actualBookDto = bookService.getBookByTitle(bookTitle);

        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @Test
    void deleteBookByTitle() {
        bookService.deleteBookByTitle("title1");

        doNothing().when(bookDao).deleteByTitle(any());

        verify(bookDao, times(1)).deleteByTitle(any());
    }

    @Test
    void insertBook() {

        BookDto expectedBookDto = createBookDtoData();

        when(inputProcessor.readString())
                .thenReturn("title1");

        when(authorService.getAuthorListFromInput()).thenReturn(
                List.of(AuthorDto.builder().name("author1").build(),
                        AuthorDto.builder().name("author2").build()
                )
        );

        when(genreService.getGenreListFromInput()).thenReturn(
                List.of(GenreDto.builder().name("genre1").build()
                )
        );

        bookService.insertBook();

        verify(bookDao, times(1)).insert(any());

        var captor = ArgumentCaptor.forClass(BookDto.class);

        verify(bookDao).insert(captor.capture());

        var actualBookDto = captor.getValue();

        assertThat(actualBookDto).isEqualTo(expectedBookDto);

    }

    @Test
    void updateBook() {

        BookUpdateDto expectedBookUpdateDto = createBookUpdateDtoData();
        Book book = createBookData();

        when(inputProcessor.readString())
                .thenReturn("title1")
                .thenReturn("updatedTitle");

        when(authorService.updateAuthorListFromInput(any())).thenReturn(
                List.of(AuthorDto.builder().name("updatedAuthor1").build(),
                        AuthorDto.builder().name("updatedAuthor2").build()
                )
        );

        when(genreService.updateGenreListFromInput(any())).thenReturn(
                List.of(GenreDto.builder().name("updatedGenre1").build()
                )
        );

        when(bookDao.getByTitle(any())).thenReturn(book);

        bookService.updateBook();

        verify(bookDao, times(1)).update(any());

        var captor = ArgumentCaptor.forClass(BookUpdateDto.class);

        verify(bookDao).update(captor.capture());

        var actualBookUpdateDto = captor.getValue();

        assertThat(actualBookUpdateDto).isEqualTo(expectedBookUpdateDto);
    }

    private List<Book> createBookListData() {
        return List.of(
                Book.builder()
                        .id(List.of(1L, 2L))
                        .title("title1")
                        .authors(List.of(
                                        Author.builder().id(1L).name("author1").build(),
                                        Author.builder().id(2L).name("author2").build()
                                )
                        )
                        .genres(List.of(
                                        Genre.builder().id(1L).name("genre1").build()
                                )
                        )
                        .build(),
                Book.builder()
                        .id(List.of(3L, 4L))
                        .title("title2")
                        .authors(List.of(
                                        Author.builder().id(3L).name("author3").build()
                                )
                        )
                        .genres(List.of(
                                        Genre.builder().id(2L).name("genre2").build(),
                                        Genre.builder().id(3L).name("genre3").build()
                                )
                        )
                        .build()
        );
    }

    private List<BookDto> createBookDtoListData() {
        return List.of(
                BookDto.builder()
                        .title("title1")
                        .authorsDto(List.of(
                                        AuthorDto.builder().name("author1").build(),
                                        AuthorDto.builder().name("author2").build()
                                )
                        )
                        .genresDto(List.of(
                                        GenreDto.builder().name("genre1").build()
                                )
                        )
                        .build(),
                BookDto.builder()
                        .title("title2")
                        .authorsDto(List.of(
                                        AuthorDto.builder().name("author3").build()
                                )
                        )
                        .genresDto(List.of(
                                        GenreDto.builder().name("genre2").build(),
                                        GenreDto.builder().name("genre3").build()
                                )
                        )
                        .build()
        );
    }

    private Book createBookData() {
        return Book.builder()
                .id(List.of(1L, 2L))
                .title("title1")
                .authors(List.of(
                                Author.builder().id(1L).name("author1").build(),
                                Author.builder().id(2L).name("author2").build()
                        )
                )
                .genres(List.of(
                                Genre.builder().id(1L).name("genre1").build()
                        )
                )
                .build();
    }

    private BookDto createBookDtoData() {
        return BookDto.builder()
                .title("title1")
                .authorsDto(List.of(
                                AuthorDto.builder().name("author1").build(),
                                AuthorDto.builder().name("author2").build()
                        )
                )
                .genresDto(List.of(
                                GenreDto.builder().name("genre1").build()
                        )
                )
                .build();
    }

    private BookUpdateDto createBookUpdateDtoData() {
        return BookUpdateDto.builder()
                .oldTitle("title1")
                .updatedTitle("updatedTitle")
                .updatedAuthorsDto(List.of(
                                AuthorDto.builder().name("updatedAuthor1").build(),
                                AuthorDto.builder().name("updatedAuthor2").build()
                        )
                )
                .updatedGenresDto(List.of(
                                GenreDto.builder().name("updatedGenre1").build()
                        )
                )
                .build();
    }

}