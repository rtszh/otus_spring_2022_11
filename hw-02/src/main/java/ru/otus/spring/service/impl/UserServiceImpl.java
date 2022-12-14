package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.IoProcessor;
import ru.otus.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final IoProcessor ioProcessor;

    public UserServiceImpl(IoProcessor ioProcessor) {
        this.ioProcessor = ioProcessor;
    }

    @Override
    public User registerNewUser() {
        ioProcessor.outputString("Enter your Last name: ");
        var lastName = ioProcessor.readString();

        ioProcessor.outputString("Enter your First name: ");
        var firstName = ioProcessor.readString();

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
