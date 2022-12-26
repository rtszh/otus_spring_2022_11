package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.RowDao;
import ru.otus.spring.domain.Row;
import ru.otus.spring.domain.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RowServiceImplTestData {

    @Mock
    private RowDao rowDao;

    @InjectMocks
    private RowServiceImpl rowService;

    @Test
    void getTest() {

        given(rowDao.readAllRows())
                .willReturn(
                        List.of(
                                new Row("Question1", "Answer1"),
                                new Row("Question2", "Answer2")
                        )
                );

        TestData correctTestDataData = TestData.of(
                List.of(
                        new Row("Question1", "Answer1"),
                        new Row("Question2", "Answer2")
                )
        );

        assertThat(rowService.getTest()).isEqualTo(correctTestDataData);
    }
}