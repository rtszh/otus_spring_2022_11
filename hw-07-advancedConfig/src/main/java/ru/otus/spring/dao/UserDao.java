package ru.otus.spring.dao;

import ru.otus.spring.domain.User;

import java.util.Optional;

public interface UserDao {
    User saveUser(User user);

    Optional<User> findUser();
}
