package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.service.TestAnalyzeService;

@Service
public class TestAnalyzeServiceImpl implements TestAnalyzeService {
    @Override
    public String getTestResultAnalysis(TestResult testResult) {

        var stringBuilder = new StringBuilder();

        stringBuilder.append("__________\n");
        stringBuilder.append(
                String.format("%s %s. Test Result:\n", testResult.getUserFirstName(), testResult.getUserLastName())
        );

        var questionAnswerList = testResult.getQuestionAnswerList();

        for (int i = 0; i < questionAnswerList.size(); i++) {

            var questionAnswer = questionAnswerList.get(i);

            stringBuilder.append(
                    String.format("Question %d: %s?\n\t User answer: %s %s (Correct answer: %s)\n",
                            i + 1,
                            questionAnswer.getQuestion(),
                            questionAnswer.getInputAnswer(),
                            convertAnswerToSymbol(questionAnswer.isAnswerCorrect()),
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
