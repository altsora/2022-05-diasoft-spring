package ru.diasoft.spring.jwtmvc.service;

import ru.diasoft.spring.jwtmvc.model.AddBookDto;
import ru.diasoft.spring.jwtmvc.model.BookDto;
import ru.diasoft.spring.jwtmvc.model.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Integer bookId);

    BookDto save(AddBookDto dto);

    BookDto update(Integer bookId, UpdateBookDto dto);

    void delete(Integer bookId);
}
