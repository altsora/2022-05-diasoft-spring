package ru.diasoft.spring.jwtmvc.utils;

import lombok.experimental.UtilityClass;
import ru.diasoft.spring.jwtmvc.domain.Book;
import ru.diasoft.spring.jwtmvc.model.BookDto;

@UtilityClass
public class Mapper {
    public static BookDto fromDomainToDto(Book domain) {
        return BookDto.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .build();
    }
}
