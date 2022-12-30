package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
public class Book {
    List<Long> id;
    String title;
    List<Author> authors;
    List<Genre> genres;
}
