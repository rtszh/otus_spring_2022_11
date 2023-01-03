package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookUpdateDto;

import java.util.List;


public interface BookDao {

    void insert(BookDto bookDto);

    void update(BookUpdateDto bookUpdateDto);

    List<Book> getAll();

    Book getByTitle(String title);

    void deleteByTitle(String title);
}
