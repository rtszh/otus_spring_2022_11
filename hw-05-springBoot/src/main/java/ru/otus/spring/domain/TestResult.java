package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TestResult {
    User user;
    List<QuestionAnswer> questionAnswerList;

    public String getUserFirstName() {
        return user.getFirstName();
    }

    public String getUserLastName() {
        return user.getLastName();
    }
}
