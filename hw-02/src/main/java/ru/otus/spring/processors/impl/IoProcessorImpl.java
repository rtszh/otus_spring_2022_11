package ru.otus.spring.processors.impl;

import ru.otus.spring.processors.IoProcessor;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoProcessorImpl implements IoProcessor {
    private final Scanner input;
    private final PrintStream output;

    public IoProcessorImpl(InputStream inputStream, PrintStream output) {
        this.input = new Scanner(inputStream);
        this.output = output;
    }

    @Override
    public String readString() {
        return input.nextLine();
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
