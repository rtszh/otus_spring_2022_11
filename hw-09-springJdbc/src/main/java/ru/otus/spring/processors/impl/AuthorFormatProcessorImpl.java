package ru.otus.spring.processors.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.processors.AuthorFormatProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorFormatProcessorImpl implements AuthorFormatProcessor {
    @Override
    public String formatAuthorList(List<AuthorDto> authorDto) {
        return authorDto.stream()
                .map(AuthorDto::getName)
                .collect(Collectors.joining(", "));
    }
}
