package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final InputProcessor inputProcessor;
    private final MessageProcessor messageProcessor;

    public UserServiceImpl(InputProcessor inputProcessor, MessageProcessor messageProcessor) {
        this.inputProcessor = inputProcessor;
        this.messageProcessor = messageProcessor;
    }

    @Override
    public User registerNewUser() {
        messageProcessor.showLocaleMessage("test.userLastName", null);
        var lastName = inputProcessor.readString();

        messageProcessor.showLocaleMessage("test.userFirstName", null);
        var firstName = inputProcessor.readString();

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
