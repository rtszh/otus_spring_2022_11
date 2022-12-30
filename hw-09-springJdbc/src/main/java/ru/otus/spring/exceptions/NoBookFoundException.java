package ru.otus.spring.exceptions;

public class NoBookFoundException extends RuntimeException {
    public NoBookFoundException(String s) {
        super(s);
    }
}
