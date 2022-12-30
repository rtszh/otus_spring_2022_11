package ru.otus.spring.processors;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookMappingDto;

import java.util.List;


public interface BookMappingProcessor {
    /**
     * method for assign one field of book entity
     *
     * @param book                  - book entity
     * @param bookMappingDtoList    - list with id of field which need to assign
     * @param clazz                 - class of field which need to assign
     * @param uniqueMappingElements - list of unique elements (entities) which need to mapping
     * @param idFieldName           - the name of the field that contains the id of element which need to mapping
     * @param <T>                   - type of element which need to mapping
     */
    <T> void mapListElementsForBook(Book book, List<BookMappingDto> bookMappingDtoList, Class<T> clazz, List<T> uniqueMappingElements, String idFieldName);
}
