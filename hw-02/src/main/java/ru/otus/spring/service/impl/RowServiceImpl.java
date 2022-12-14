package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.RowDao;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.service.RowService;

@Service
public class RowServiceImpl implements RowService {

    private final RowDao rowDao;

    public RowServiceImpl(RowDao rowDao) {
        this.rowDao = rowDao;
    }

    @Override
    public TestData getTest() {
        return TestData.of(rowDao.readAllRows());
    }
}
