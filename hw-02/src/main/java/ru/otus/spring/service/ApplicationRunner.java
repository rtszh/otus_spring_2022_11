package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.processors.IoProcessor;

@Component
public class ApplicationRunner {

    private final RowService rowService;
    private final IoProcessor ioProcessor;

    private final UserService userService;

    private final TestResultService testResultService;

    private final TestAnalyzeService testAnalyzeService;

    @Autowired
    public ApplicationRunner(RowService rowService, IoProcessor ioProcessor, UserService userService,
                             TestResultService testResultService, TestAnalyzeService testAnalyzeService) {
        this.rowService = rowService;
        this.ioProcessor = ioProcessor;
        this.userService = userService;
        this.testResultService = testResultService;
        this.testAnalyzeService = testAnalyzeService;
    }

    public void run() {

        ioProcessor.outputString("Registration for Test:");
        var user = userService.registerNewUser();
        var test = rowService.getTest();

        var testResult = testResultService.runTest(user, test);

        showTestResult(testAnalyzeService.getTestResultAnalysis(testResult));
    }

    private void showTestResult(String testResultAnalysis) {
        ioProcessor.outputString(testResultAnalysis);
    }

}
