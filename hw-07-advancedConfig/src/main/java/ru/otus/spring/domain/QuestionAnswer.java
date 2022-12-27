package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionAnswer {
    String question;
    String inputAnswer;
    String correctAnswer;
    boolean isAnswerCorrect;
}
