package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.processors.AuthorFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.AuthorService;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService {

    private final OutputProcessor outputProcessor;
    private final InputProcessor inputProcessor;

    private final AuthorFormatProcessor authorFormatProcessor;

    @Autowired
    public AuthorServiceImpl(OutputProcessor outputProcessor, InputProcessor inputProcessor,
                             AuthorFormatProcessor authorFormatProcessor) {
        this.outputProcessor = outputProcessor;
        this.inputProcessor = inputProcessor;
        this.authorFormatProcessor = authorFormatProcessor;
    }

    @Override
    public List<AuthorDto> getAuthorListFromInput() {
        List<AuthorDto> authorList = new ArrayList<>();
        String input;

        outputProcessor.outputString("enter book authors (input 'end' to stop): ");

        while (!(input = inputProcessor.readString()).equalsIgnoreCase("end")) {
            authorList.add(
                    AuthorDto.builder().name(input).build()
            );
        }

        return authorList;
    }

    @Override
    public List<AuthorDto> updateAuthorListFromInput(List<AuthorDto> authorDtoList) {
        outputProcessor.outputString("Current authors: " + authorFormatProcessor.formatAuthorList(authorDtoList));

        return getAuthorListFromInput();
    }
}
