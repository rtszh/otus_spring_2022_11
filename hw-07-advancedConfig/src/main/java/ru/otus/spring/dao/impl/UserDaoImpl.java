package ru.otus.spring.dao.impl;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.UserDao;
import ru.otus.spring.domain.User;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    @Nullable
    private User savedUser;

    @Override
    public User saveUser(User user) {
        savedUser = user;
        return savedUser;
    }

    @Override
    public Optional<User> findUser() {
        return Optional.ofNullable(savedUser);
    }
}
