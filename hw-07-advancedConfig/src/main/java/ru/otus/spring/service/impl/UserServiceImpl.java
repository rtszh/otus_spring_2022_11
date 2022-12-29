package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.UserDao;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final InputProcessor inputProcessor;
    private final MessageProcessor messageProcessor;

    private final UserDao userDao;

    public UserServiceImpl(InputProcessor inputProcessor, MessageProcessor messageProcessor, UserDao userDao) {
        this.inputProcessor = inputProcessor;
        this.messageProcessor = messageProcessor;
        this.userDao = userDao;
    }

    @Override
    public User registerNewUser() {
        messageProcessor.showLocaleMessage("test.userLastName", null);
        var lastName = inputProcessor.readString();

        messageProcessor.showLocaleMessage("test.userFirstName", null);
        var firstName = inputProcessor.readString();

        return userDao.saveUser(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build()
        );
    }

    @Override
    public Optional<User> findUser() {
        return userDao.findUser();
    }
}
