package ru.otus.spring.service;

import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.domain.User;

import java.util.Optional;

public interface TestResultService {
    TestResult runTest(User user, TestData testData);

    Optional<TestResult> findTestResult();
}
