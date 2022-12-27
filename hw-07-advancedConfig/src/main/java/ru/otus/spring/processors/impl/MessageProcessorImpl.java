package ru.otus.spring.processors.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProperties;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.processors.OutputProcessor;

@Component
public class MessageProcessorImpl implements MessageProcessor {

    private final MessageSource messageSource;

    private final AppProperties properties;
    private final OutputProcessor outputProcessor;

    public MessageProcessorImpl(MessageSource messageSource, @Qualifier("appProperties") AppProperties properties,
                                OutputProcessor outputProcessor) {
        this.messageSource = messageSource;
        this.properties = properties;
        this.outputProcessor = outputProcessor;
    }

    @Override
    public void showLocaleMessage(String code, @Nullable Object[] args) {
        var localizedMessage = messageSource.getMessage(code, args, properties.getLocale());
        outputProcessor.outputString(localizedMessage);
    }

    @Override
    public String getLocaleMessage(String code, @Nullable Object[] args) {
        return messageSource.getMessage(code, args, properties.getLocale());
    }
}
