package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.QuestionAnswer;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.InputProcessor;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.TestResultService;

import java.util.ArrayList;

@Service
public class TestResultServiceImpl implements TestResultService {


    private final InputProcessor inputProcessor;
    private final OutputProcessor outputProcessor;
    private final MessageProcessor messageProcessor;

    public TestResultServiceImpl(InputProcessor inputProcessor, OutputProcessor outputProcessor,
                                 MessageProcessor messageProcessor) {
        this.inputProcessor = inputProcessor;
        this.outputProcessor = outputProcessor;
        this.messageProcessor = messageProcessor;
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
        return inputProcessor.readString();
    }

    private void showQuestion(int questionNumber, String question) {
        var questionPhrase = messageProcessor.getLocaleMessage("test.result.questionPhrase", null);

        outputProcessor.outputString(
                String.format("%s %d: %s?", questionPhrase, questionNumber, question)
        );
    }
}


