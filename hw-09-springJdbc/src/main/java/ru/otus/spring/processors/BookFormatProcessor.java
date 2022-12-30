package ru.otus.spring.processors;

import ru.otus.spring.dto.BookDto;

import java.util.List;

public interface BookFormatProcessor {
    String formatBookList(List<BookDto> bookDto);
}
