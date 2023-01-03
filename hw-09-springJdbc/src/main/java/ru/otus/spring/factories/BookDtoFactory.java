package ru.otus.spring.factories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookMappingDto;
import ru.otus.spring.dto.BookRawDto;
import ru.otus.spring.dto.BooksMappingDto;

import java.util.List;
import java.util.stream.Collectors;


public class BookDtoFactory {
    public static BookRawDto createBookRawDto(Long bookId, Long authorId, Long genreId, String title) {
        return BookRawDto.builder()
                .bookId(bookId)
                .authorId(authorId)
                .genreId(genreId)
                .title(title)
                .build();
    }

    public static BookMappingDto createBookMappingDto(String title, Long mappingId) {
        return BookMappingDto.builder()
                .mappingId(mappingId)
                .bookTitle(title)
                .build();
    }

    public static BooksMappingDto createBooksMappingDto(List<BookRawDto> bookRawDtoList, List<Author> authors, List<Genre> genres) {
        return BooksMappingDto.builder()
                .bookRawDtoList(bookRawDtoList)
                .authors(authors)
                .genres(genres)
                .build();
    }

    public static BookDto createBookDto(String title, List<Author> authors, List<Genre> genres) {
        return BookDto.builder()
                .title(title)
                .authorsDto(
                        authors.stream()
                                .map(AuthorDtoFactory::createAuthorDto)
                                .collect(Collectors.toList())
                )
                .genresDto(genres.stream()
                        .map(GenreDtoFactory::createGenreDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

}
