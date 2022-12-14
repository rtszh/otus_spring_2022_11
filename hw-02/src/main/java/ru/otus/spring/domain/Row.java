package ru.otus.spring.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Row {
    @CsvBindByPosition(position = 0)
    private String question;
    @CsvBindByPosition(position = 1)
    private String correctAnswer;
}
