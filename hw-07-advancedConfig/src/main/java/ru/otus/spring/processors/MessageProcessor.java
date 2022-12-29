package ru.otus.spring.processors;

public interface MessageProcessor {

    void showLocaleMessage(String code, Object[] args);

    String getLocaleMessage(String code, Object[] args);
}
