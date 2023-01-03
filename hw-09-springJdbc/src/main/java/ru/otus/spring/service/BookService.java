package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;

import java.util.List;


public interface BookService {
    List<BookDto> getAllBooks();

    String getAllBooksAsString();

    BookDto getBookByTitle(String bookTitle);

    String getBookByTitleAsString(String bookTitle);

    void deleteBookByTitle(String bookTitle);

    void insertBook();

    void updateBook();
}
