package ru.otus.spring.processors.impl;

import ru.otus.spring.processors.InputProcessor;

import java.io.InputStream;
import java.util.Scanner;

public class InputProcessorImpl implements InputProcessor {

    private final Scanner input;

    public InputProcessorImpl(InputStream inputStream) {
        this.input = new Scanner(inputStream);
    }

    @Override
    public String readString() {
        return input.nextLine();
    }
}
