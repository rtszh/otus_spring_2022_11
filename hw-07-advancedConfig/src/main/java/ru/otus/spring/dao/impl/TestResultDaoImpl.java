package ru.otus.spring.dao.impl;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.TestResultDao;
import ru.otus.spring.domain.TestResult;

import java.util.Optional;

@Component
public class TestResultDaoImpl implements TestResultDao {

    @Nullable
    private TestResult savedTestResult;

    @Override
    public TestResult saveTestResult(TestResult testResult) {
        savedTestResult = testResult;
        return savedTestResult;
    }

    @Override
    public Optional<TestResult> findTestResult() {
        return Optional.ofNullable(savedTestResult);
    }
}
