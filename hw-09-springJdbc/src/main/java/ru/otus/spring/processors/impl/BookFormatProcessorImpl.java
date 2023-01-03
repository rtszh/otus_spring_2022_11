package ru.otus.spring.processors.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.processors.AuthorFormatProcessor;
import ru.otus.spring.processors.BookFormatProcessor;
import ru.otus.spring.processors.GenreFormatProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookFormatProcessorImpl implements BookFormatProcessor {

    private final AuthorFormatProcessor authorFormatProcessor;
    private final GenreFormatProcessor genreFormatProcessor;

    public BookFormatProcessorImpl(AuthorFormatProcessor authorFormatProcessor,
                                   GenreFormatProcessor genreFormatProcessor) {
        this.authorFormatProcessor = authorFormatProcessor;
        this.genreFormatProcessor = genreFormatProcessor;
    }

    @Override
    public String formatBookList(List<BookDto> bookDto) {
        return bookDto.stream()
                .map(this::convertBookDto)
                .collect(Collectors.joining("\n"));
    }

    private String convertBookDto(BookDto bookDto) {

        String authors = authorFormatProcessor.formatAuthorList(bookDto.getAuthorsDto());

        String genres = genreFormatProcessor.formatGenreList(bookDto.getGenresDto());

        return String.format(
                "title: %s; authors: %s; genres: %s",
                bookDto.getTitle(),
                authors,
                genres
        );
    }
}
