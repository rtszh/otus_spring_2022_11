package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.service.TestAnalyzeService;

@Service
public class TestAnalyzeServiceImpl implements TestAnalyzeService {

    private final MessageProcessor messageProcessor;

    public TestAnalyzeServiceImpl(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public String getTestResultAnalysis(TestResult testResult) {

        var stringBuilder = new StringBuilder();
        var testResultPhrase = messageProcessor.getLocaleMessage("test.result.phrase", null);
        var questionPhrase = messageProcessor.getLocaleMessage("test.result.questionPhrase", null);
        var userAnswerPhrase = messageProcessor.getLocaleMessage("test.result.userAnswer", null);
        var correctAnswerPhrase = messageProcessor.getLocaleMessage("test.result.correctAnswer", null);

        stringBuilder.append("__________\n");
        stringBuilder.append(
                String.format("%s %s. %s:\n", testResult.getUserFirstName(), testResult.getUserLastName(), testResultPhrase)
        );

        var questionAnswerList = testResult.getQuestionAnswerList();

        for (int i = 0; i < questionAnswerList.size(); i++) {

            var questionAnswer = questionAnswerList.get(i);

            stringBuilder.append(
                    String.format("%s %d: %s?\n\t %s: %s %s (%s: %s)\n",
                            questionPhrase,
                            i + 1,
                            questionAnswer.getQuestion(),
                            userAnswerPhrase,
                            questionAnswer.getInputAnswer(),
                            convertAnswerToSymbol(questionAnswer.isAnswerCorrect()),
                            correctAnswerPhrase,
                            questionAnswer.getCorrectAnswer()
                    )
            );
        }

        return stringBuilder.toString();
    }

    String convertAnswerToSymbol(boolean isAnswerCorrect) {
        if (isAnswerCorrect) {
            return "+";
        } else {
            return "-";
        }
    }
}
