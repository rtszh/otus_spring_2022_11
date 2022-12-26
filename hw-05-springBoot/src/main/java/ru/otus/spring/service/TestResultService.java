package ru.otus.spring.service;

import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.TestResult;
import ru.otus.spring.domain.User;

public interface TestResultService {
    TestResult runTest(User user, TestData testData);
}
