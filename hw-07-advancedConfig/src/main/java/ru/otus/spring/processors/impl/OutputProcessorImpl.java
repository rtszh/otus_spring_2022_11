package ru.otus.spring.processors.impl;

import ru.otus.spring.processors.OutputProcessor;

import java.io.PrintStream;

public class OutputProcessorImpl implements OutputProcessor {

    private final PrintStream output;

    public OutputProcessorImpl(PrintStream output) {
        this.output = output;
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
