package ru.diasoft.spring.docker.utils;

import lombok.experimental.UtilityClass;
import ru.diasoft.spring.docker.domain.Book;
import ru.diasoft.spring.docker.model.BookDto;

@UtilityClass
public class Mapper {
    public static BookDto toDto(Book domain) {
        return BookDto.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .build();
    }
}
