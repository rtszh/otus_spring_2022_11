package ru.otus.spring.service;


import ru.otus.spring.domain.User;

import java.util.Optional;

public interface UserService {
    User registerNewUser();
    Optional<User> findUser();
}
