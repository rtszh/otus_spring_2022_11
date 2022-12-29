package ru.otus.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.User;
import ru.otus.spring.processors.MessageProcessor;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.RowService;
import ru.otus.spring.service.TestAnalyzeService;
import ru.otus.spring.service.TestResultService;
import ru.otus.spring.service.UserService;

@ShellComponent
public class ShellCommands {

    private final RowService rowService;
    private final OutputProcessor outputProcessor;

    private final UserService userService;

    private final TestResultService testResultService;

    private final TestAnalyzeService testAnalyzeService;

    private final MessageProcessor messageProcessor;

    public ShellCommands(RowService rowService, OutputProcessor outputProcessor, UserService userService,
                         TestResultService testResultService, TestAnalyzeService testAnalyzeService,
                         MessageProcessor messageProcessor) {
        this.rowService = rowService;
        this.outputProcessor = outputProcessor;
        this.userService = userService;
        this.testResultService = testResultService;
        this.testAnalyzeService = testAnalyzeService;
        this.messageProcessor = messageProcessor;
    }

    @ShellMethod(value = "Command for registration new user", key = {"r", "register"})
    public void register() {
        messageProcessor.showLocaleMessage("test.registration", null);
        userService.registerNewUser();
    }

    @ShellMethod(value = "Command run the test", key = {"t", "test"})
    public void test(@ShellOption(defaultValue = "Unknown User") String unknownUser) {
        var user = userService.findUser()
                .orElse(
                        User.builder().firstName(unknownUser).lastName("-").build()
                );

        var test = rowService.getTest();

        testResultService.runTest(user, test);
    }

    @ShellMethod(value = "Command to show current user info", key = {"sui", "showUserInfo"})
    public void showCurrentUser() {
        userService.findUser().ifPresentOrElse(
                System.out::println,
                () -> System.out.println("You need to register before show user info!")
        );
    }

    @ShellMethod(value = "Command to show results of the test", key = {"str", "showTestResult"})
    @ShellMethodAvailability(value = "isTestResultAvailable")
    public void showTestResult() {
        showTestResultToShell(testAnalyzeService.getTestResultAnalysis(
                testResultService.findTestResult().get())
        );
    }

    private Availability isTestResultAvailable() {
        return testResultService.findTestResult().isEmpty() ? Availability.unavailable("before take the result user needs to register and pass the test!") : Availability.available();
    }

    private void showTestResultToShell(String testResultAnalysis) {
        outputProcessor.outputString(testResultAnalysis);
    }

}
