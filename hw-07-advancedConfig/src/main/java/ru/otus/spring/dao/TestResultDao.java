package ru.otus.spring.dao;

import ru.otus.spring.domain.TestResult;

import java.util.Optional;

public interface TestResultDao {
    TestResult saveTestResult(TestResult testResult);

    Optional<TestResult> findTestResult();
}
