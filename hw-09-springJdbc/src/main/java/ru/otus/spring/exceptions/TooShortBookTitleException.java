package ru.otus.spring.exceptions;

public class TooShortBookTitleException extends RuntimeException {
    public TooShortBookTitleException(String s) {
        super(s);
    }
}
