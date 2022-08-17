package ru.diasoft.spring.booklibrarymvc.service;

import ru.diasoft.spring.booklibrarymvc.model.AddBookDto;
import ru.diasoft.spring.booklibrarymvc.model.BookDto;
import ru.diasoft.spring.booklibrarymvc.model.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(Long bookId);

    BookDto addBook(AddBookDto dto);

    BookDto updateBook(Long bookId, UpdateBookDto dto);

    void deleteBookById(Long bookId);
}
