package ru.otus.spring.processors.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookMappingDto;
import ru.otus.spring.exceptions.BookMappingException;
import ru.otus.spring.processors.BookMappingProcessor;
import ru.otus.spring.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BookMappingProcessorImpl implements BookMappingProcessor {
    @Override
    public <T> void mapListElementsForBook(Book book, List<BookMappingDto> bookMappingDtoList, Class<T> clazz, List<T> uniqueMappingElements, String idFieldName) {
        List<T> objectList = getListObjectFromFieldByName(book, clazz.getTypeName());

        var idField = FieldUtils.getDeclaredField(clazz, idFieldName, true);

        var filteredBookMappingDtoList = bookMappingDtoList.stream()
                .filter(bookMappingDto -> bookMappingDto.getBookTitle().equals(book.getTitle()))
                .collect(Collectors.toList());

        Map<T, String> objectTitleMap = getObjectTitleMap(filteredBookMappingDtoList, uniqueMappingElements, idField);

        objectTitleMap.forEach((obj, title) -> {
            if (title.equals(book.getTitle())) {
                objectList.add(obj);
            }
        });
    }

    private <T> Map<T, String> getObjectTitleMap(List<BookMappingDto> filteredBookMappingDtoList, List<T> uniqueMappingElements, Field idField) {
        Map<T, String> objectTitleMap = new HashMap<>();

        for (var bookMappingDto : filteredBookMappingDtoList) {

            var mappingId = bookMappingDto.getMappingId();

            var mappingObj = uniqueMappingElements.stream()
                    .filter(element -> ReflectionUtils.getPrivateField(idField, element).equals(mappingId))
                    .findFirst()
                    .orElseThrow(BookMappingException::new);

            objectTitleMap.put(mappingObj, bookMappingDto.getBookTitle());
        }

        return objectTitleMap;
    }

    private <T> List<T> getListObjectFromFieldByName(Book book, String matchingFieldName) {
        var bookFields = book.getClass().getDeclaredFields();

        var field = Arrays.stream(bookFields)
                .filter(field1 -> field1.getGenericType().getTypeName().contains(matchingFieldName))
                .findFirst()
                .orElseThrow(BookMappingException::new);

        return (List<T>) ReflectionUtils.getPrivateField(field, book);
    }

}
