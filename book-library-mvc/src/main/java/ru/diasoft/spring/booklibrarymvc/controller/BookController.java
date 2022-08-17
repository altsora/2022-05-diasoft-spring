package ru.diasoft.spring.booklibrarymvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.booklibrarymvc.model.AddBookDto;
import ru.diasoft.spring.booklibrarymvc.model.BookDto;
import ru.diasoft.spring.booklibrarymvc.model.UpdateBookDto;
import ru.diasoft.spring.booklibrarymvc.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> findAllBook() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto findBookById(@PathVariable("id") Long bookId) {
        return bookService.getById(bookId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody AddBookDto request) {
        return bookService.addBook(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("id") Long bookId, @RequestBody UpdateBookDto dto) {
        return bookService.updateBook(bookId, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookById(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);
    }
}
