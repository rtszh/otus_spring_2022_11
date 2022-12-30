package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthorDto {
    String name;
}
