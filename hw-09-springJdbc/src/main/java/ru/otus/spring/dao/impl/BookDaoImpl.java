package ru.otus.spring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.*;
import ru.otus.spring.exceptions.IncorrectBookDataFromDaoException;
import ru.otus.spring.exceptions.NoBookFoundException;
import ru.otus.spring.processors.BookMappingProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.spring.factories.BookDtoFactory.createBookMappingDto;
import static ru.otus.spring.factories.BookDtoFactory.createBookRawDto;

@Component
public class BookDaoImpl implements BookDao {

    private final BookMappingProcessor bookMappingProcessor;
    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Autowired
    public BookDaoImpl(BookMappingProcessor bookMappingProcessor, NamedParameterJdbcOperations jdbc,
                       AuthorDao authorDao, GenreDao genreDao) {
        this.bookMappingProcessor = bookMappingProcessor;
        this.jdbc = jdbc;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void insert(BookDto bookDto) {
        List<Author> authors = getInsertedAuthors(bookDto.getAuthorsDto());
        List<Genre> genres = getInsertedGenres(bookDto.getGenresDto());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", bookDto.getTitle());

        authors.forEach(author -> {
            genres.forEach(genre -> {
                params.addValue("authorId", author.getId());
                params.addValue("genreId", genre.getId());

                KeyHolder kh = new GeneratedKeyHolder();

                jdbc.update("INSERT INTO book (title, author_id, genre_id) values (:title, :authorId, :genreId)",
                        params,
                        kh,
                        new String[]{"id"}
                );
            });
        });
    }

    @Override
    public List<Book> getAll() {
        List<BookRawDto> rawBookData = getRawBookData();

        return getBooksFromRawBookData(rawBookData);
    }

    @Override
    public Book getByTitle(String title) {
        List<BookRawDto> rawBookDataByTitle = getRawBookDataById(title);

        var bookList = getBooksFromRawBookData(rawBookDataByTitle);

        checkBookListSize(bookList);

        return bookList.get(0);
    }

    @Override
    public void deleteByTitle(String title) {
        Map<String, String> params = Collections.singletonMap("title", title);

        jdbc.update(
                "DELETE FROM book WHERE title = :title",
                params
        );
    }

    @Override
    public void update(BookUpdateDto bookUpdateDto) {
        deleteByTitle(bookUpdateDto.getOldTitle());

        var bookDto = BookDto.builder()
                .title(bookUpdateDto.getUpdatedTitle())
                .authorsDto(bookUpdateDto.getUpdatedAuthorsDto())
                .genresDto(bookUpdateDto.getUpdatedGenresDto())
                .build();

        insert(bookDto);
    }

    private List<Genre> getInsertedGenres(List<GenreDto> genreDtoList) {
        return genreDtoList.stream()
                .map(genreDao::insert)
                .collect(Collectors.toList());
    }

    private List<Author> getInsertedAuthors(List<AuthorDto> authorDtoList) {
        return authorDtoList.stream()
                .map(authorDao::insert)
                .collect(Collectors.toList());
    }

    private void checkBookListSize(List<Book> bookList) {
        if (bookList.size() > 1) {
            throw new IncorrectBookDataFromDaoException("The number of books with the same title is more than 1");
        } else if (bookList.size() < 1) {
            throw new NoBookFoundException("No books with this title found");
        }
    }

    private List<BookRawDto> getRawBookData() {
        return jdbc.query(
                "SELECT id, title, author_id, genre_id FROM book",
                new BookMapper()
        );
    }

    private List<BookRawDto> getRawBookDataById(String title) {

        Map<String, String> params = Collections.singletonMap("title", title);

        return jdbc.query(
                "SELECT id, title, author_id, genre_id FROM book WHERE title = :title",
                params,
                new BookMapper()
        );
    }

    private List<Book> getBooksFromRawBookData(List<BookRawDto> bookRawDtoList) {

        List<Book> books = createBooksWithUniqueTitle(bookRawDtoList);

        assignBooksId(books, bookRawDtoList);

        assignBooksAuthors(books, bookRawDtoList);

        assignBooksGenres(books, bookRawDtoList);

        return books;
    }

    private void assignBooksId(List<Book> books, List<BookRawDto> bookRawDtoList) {
        Map<String, List<Long>> titleIdRawMap = bookRawDtoList.stream()
                .collect(Collectors.groupingBy(
                                BookRawDto::getTitle,
                                Collectors.mapping(BookRawDto::getBookId, Collectors.toList())
                        )
                );

        books.forEach(book -> {
            List<Long> newIds = titleIdRawMap.get(book.getTitle());

            List<Long> curIds = book.getId();

            curIds.addAll(newIds);
        });

    }

    private void assignBooksAuthors(List<Book> books, List<BookRawDto> bookRawDtoList) {
        List<Author> rawBookDataAuthors = getAuthorsFromRawBookData(bookRawDtoList);

        List<BookMappingDto> bookMappingAuthorDtoList = bookRawDtoList.stream()
                .map(bookRawDto -> createBookMappingDto(bookRawDto.getTitle(), bookRawDto.getAuthorId()))
                .collect(Collectors.toList());

        books.forEach((book -> bookMappingProcessor.mapListElementsForBook(book, bookMappingAuthorDtoList, Author.class, rawBookDataAuthors, "id")));
    }

    private void assignBooksGenres(List<Book> books, List<BookRawDto> bookRawDtoList) {
        List<Genre> rawBookDataGenres = getGenresFromRawBookData(bookRawDtoList);

        List<BookMappingDto> bookMappingGenreDtoList = bookRawDtoList.stream()
                .map(bookRawDto -> createBookMappingDto(bookRawDto.getTitle(), bookRawDto.getGenreId()))
                .collect(Collectors.toList());

        books.forEach((book -> bookMappingProcessor.mapListElementsForBook(book, bookMappingGenreDtoList, Genre.class, rawBookDataGenres, "id")));

    }

    private List<Book> createBooksWithUniqueTitle(List<BookRawDto> bookRawDtoList) {
        return bookRawDtoList.stream()
                .map(bookRawDto -> bookRawDto.getTitle())
                .distinct()
                .map(title -> Book.builder()
                        .id(new ArrayList<>())
                        .title(title)
                        .authors(new ArrayList<>())
                        .genres(new ArrayList<>())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<Author> getAuthorsFromRawBookData(List<BookRawDto> bookRawDtoList) {
        return bookRawDtoList.stream()
                .map(BookRawDto::getAuthorId)
                .map(authorDao::getById)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Genre> getGenresFromRawBookData(List<BookRawDto> bookRawDtoList) {
        return bookRawDtoList.stream()
                .map(BookRawDto::getGenreId)
                .map(genreDao::getById)
                .distinct()
                .collect(Collectors.toList());
    }

    private static class BookMapper implements RowMapper<BookRawDto> {
        @Override
        public BookRawDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long bookId = rs.getLong("id");
            Long authorId = rs.getLong("author_id");
            Long genreId = rs.getLong("genre_id");
            String title = rs.getString("title");
            return createBookRawDto(bookId, authorId, genreId, title);
        }
    }
}
