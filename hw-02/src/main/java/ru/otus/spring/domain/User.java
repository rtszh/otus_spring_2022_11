package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    String firstName;
    String lastName;
}
