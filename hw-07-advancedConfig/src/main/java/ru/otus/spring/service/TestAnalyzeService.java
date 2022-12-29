package ru.otus.spring.service;

import ru.otus.spring.domain.TestResult;

public interface TestAnalyzeService {
    String getTestResultAnalysis(TestResult testResult);
}
