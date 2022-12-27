package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.processors.OutputProcessor;

@Component
public class ApplicationRunner {

    private final RowService rowService;
    private final OutputProcessor outputProcessor;

    private final UserService userService;

    private final TestResultService testResultService;

    private final TestAnalyzeService testAnalyzeService;

    private final MessageProcessor messageProcessor;

    @Autowired
    public ApplicationRunner(RowService rowService, OutputProcessor outputProcessor, UserService userService,
                             TestResultService testResultService, TestAnalyzeService testAnalyzeService,
                             MessageProcessor messageProcessor) {
        this.rowService = rowService;
        this.outputProcessor = outputProcessor;
        this.userService = userService;
        this.testResultService = testResultService;
        this.testAnalyzeService = testAnalyzeService;
        this.messageProcessor = messageProcessor;
    }


    public void run() {

        messageProcessor.showLocaleMessage("test.registration", null);
        var user = userService.registerNewUser();
        var test = rowService.getTest();

        var testResult = testResultService.runTest(user, test);

        showTestResult(testAnalyzeService.getTestResultAnalysis(testResult));
    }

    private void showTestResult(String testResultAnalysis) {
        outputProcessor.outputString(testResultAnalysis);
    }

}
