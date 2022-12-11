package ru.otus.spring.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Row {
    @CsvBindByPosition(position = 0)
    private String question;
    @CsvBindByPosition(position = 1)
    private String answer;
}
