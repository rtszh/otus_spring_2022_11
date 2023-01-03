package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.exceptions.TooShortBookTitleException;
import ru.otus.spring.processors.BookFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.factories.BookDtoFactory.createBookDto;


@Service
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookDao bookDao;
    private final BookFormatProcessor bookFormatProcessor;
    private final OutputProcessor outputProcessor;
    private final InputProcessor inputProcessor;

    @Autowired
    public BookServiceImpl(AuthorService authorService, GenreService genreService, BookDao bookDao,
                           OutputProcessor outputProcessor, InputProcessor inputProcessor,
                           BookFormatProcessor bookFormatProcessor) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookDao = bookDao;
        this.outputProcessor = outputProcessor;
        this.inputProcessor = inputProcessor;
        this.bookFormatProcessor = bookFormatProcessor;
    }

    @Override
    public List<BookDto> getAllBooks() {

        var books = bookDao.getAll();

        return books.stream()
                .map(book -> createBookDto(
                                book.getTitle(), book.getAuthors(), book.getGenres()
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getAllBooksAsString() {
        return bookFormatProcessor.formatBookList(
                getAllBooks()
        );
    }

    @Override
    public BookDto getBookByTitle(String bookTitle) {

        var book = bookDao.getByTitle(bookTitle);

        return createBookDto(book.getTitle(), book.getAuthors(), book.getGenres());
    }

    @Override
    public String getBookByTitleAsString(String bookTitle) {
        return bookFormatProcessor.formatBookList(
                List.of(getBookByTitle(bookTitle))
        );
    }

    @Override
    public void deleteBookByTitle(String bookTitle) {
        checkBookTitleExists(bookTitle);
        bookDao.deleteByTitle(bookTitle);
    }

    @Override
    public void insertBook() {
        var inputBookTitle = getBookTitleFromInput();
        checkInputString(inputBookTitle);

        List<AuthorDto> inputAuthorList = authorService.getAuthorListFromInput();

        List<GenreDto> inputGenreList = genreService.getGenreListFromInput();

        var bookDto = BookDto.builder()
                .title(inputBookTitle)
                .authorsDto(inputAuthorList)
                .genresDto(inputGenreList)
                .build();

        bookDao.insert(bookDto);
    }

    @Override
    public void updateBook() {
        ;
        var oldBookTitle = getBookTitleFromInput(
                "Enter the title of the book to be updated:"
        );

        var oldBookDto = getBookByTitle(oldBookTitle);

        var inputBookTitle = getBookTitleFromInput(
                "Enter new book title:"
        );
        var updatedBookTitle = checkInputString(oldBookTitle, inputBookTitle);

        List<AuthorDto> updatedAuthorList = authorService.updateAuthorListFromInput(oldBookDto.getAuthorsDto());
        List<GenreDto> updatedGenresList = genreService.updateGenreListFromInput(oldBookDto.getGenresDto());

        var bookUpdateDto = BookUpdateDto.builder()
                .oldTitle(oldBookTitle)
                .updatedTitle(updatedBookTitle)
                .updatedAuthorsDto(updatedAuthorList)
                .updatedGenresDto(updatedGenresList)
                .build();

        bookDao.update(bookUpdateDto);
    }

    private String checkInputString(String oldBookTitle, String inputBookTitle) {
        if (inputBookTitle.length() == 0) {
            return oldBookTitle;
        } else {
            return inputBookTitle;
        }
    }

    private void checkInputString(String inputBookTitle) {
        if (inputBookTitle.length() == 0) {
            throw new TooShortBookTitleException("Title length must be greater than 0");
        }
    }

    private String getBookTitleFromInput() {
        outputProcessor.outputString("enter book title: ");
        return inputProcessor.readString();
    }

    private String getBookTitleFromInput(String s) {
        outputProcessor.outputString(s);
        return inputProcessor.readString();
    }

    private void checkBookTitleExists(String bookTitle) {
        getBookByTitle(bookTitle);
    }
}
