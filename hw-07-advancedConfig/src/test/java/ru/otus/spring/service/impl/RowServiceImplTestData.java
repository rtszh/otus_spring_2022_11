package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.RowDao;
import ru.otus.spring.domain.Row;
import ru.otus.spring.domain.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RowServiceImpl.class)
class RowServiceImplTestData {

    @MockBean
    private RowDao rowDao;

    @Autowired
    private RowServiceImpl rowService;

    @Test
    void getTest() {

        when(rowDao.readAllRows())
                .thenReturn(
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