package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Genre {
    Long id;
    String name;
}
