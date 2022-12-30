package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.processors.GenreFormatProcessor;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.GenreService;

import java.util.ArrayList;
import java.util.List;


@Service
public class GenreServiceImpl implements GenreService {

    private final OutputProcessor outputProcessor;
    private final InputProcessor inputProcessor;

    private final GenreFormatProcessor genreFormatProcessor;

    @Autowired
    public GenreServiceImpl(OutputProcessor outputProcessor, InputProcessor inputProcessor,
                            GenreFormatProcessor genreFormatProcessor) {
        this.outputProcessor = outputProcessor;
        this.inputProcessor = inputProcessor;
        this.genreFormatProcessor = genreFormatProcessor;
    }

    @Override
    public List<GenreDto> getGenreListFromInput() {
        List<GenreDto> genreList = new ArrayList<>();
        String input;

        outputProcessor.outputString("enter book genres (input 'end' to stop): ");

        while (!(input = inputProcessor.readString()).equalsIgnoreCase("end")) {
            genreList.add(
                    GenreDto.builder().name(input).build()
            );
        }

        return genreList;
    }

    @Override
    public List<GenreDto> updateGenreListFromInput(List<GenreDto> genreDtoList) {
        outputProcessor.outputString("Current authors: " + genreFormatProcessor.formatGenreList(genreDtoList));
        return getGenreListFromInput();
    }
}
