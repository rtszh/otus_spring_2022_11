package ru.otus.spring.exceptions;

public class IncorrectBookDataFromDaoException extends RuntimeException {
    public IncorrectBookDataFromDaoException(String s) {
        super(s);
    }
}
