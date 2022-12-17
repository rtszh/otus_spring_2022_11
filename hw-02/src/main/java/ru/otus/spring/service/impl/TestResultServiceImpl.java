package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.QuestionAnswer;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.IoProcessor;
import ru.otus.spring.service.TestResultService;

import java.util.ArrayList;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final IoProcessor ioProcessor;

    public TestResultServiceImpl(IoProcessor ioProcessor) {
        this.ioProcessor = ioProcessor;
    }

    @Override
    public TestResult runTest(User user, TestData testData) {
        var questions = testData.getRows();
        var userQuestionAnswerList = new ArrayList<QuestionAnswer>();

        for (int i = 0; i < questions.size(); i++) {

            var row = questions.get(i);

            var question = row.getQuestion();

            showQuestion(i + 1, question);

            String userAnswer = getUserAnswer();

            userQuestionAnswerList.add(QuestionAnswer.builder()
                    .question(question)
                    .correctAnswer(row.getCorrectAnswer())
                    .inputAnswer(userAnswer)
                    .isAnswerCorrect(
                            isAnswerCorrect(row.getCorrectAnswer(), userAnswer)
                    )
                    .build()
            );
        }

        return TestResult.builder()
                .user(user)
                .questionAnswerList(userQuestionAnswerList)
                .build();
    }

    private boolean isAnswerCorrect(String correctAnswer, String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer);
    }

    private String getUserAnswer() {
        return ioProcessor.readString();
    }

    private void showQuestion(int questionNumber, String question) {
        ioProcessor.outputString(
                String.format("Question %d: %s?", questionNumber, question)
        );
    }
}


