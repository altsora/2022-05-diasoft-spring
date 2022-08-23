package ru.diasoft.spring.jwtmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.jwtmvc.model.AddBookDto;
import ru.diasoft.spring.jwtmvc.model.BookDto;
import ru.diasoft.spring.jwtmvc.model.UpdateBookDto;
import ru.diasoft.spring.jwtmvc.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/rand")
    public String getRandomBook() {
        return "Показ случайной книги для всех";
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GUEST', 'ROLE_USER', 'ROLE_ADMIN')")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST', 'ROLE_USER', 'ROLE_ADMIN')")
    public BookDto getBookById(@PathVariable("id") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody AddBookDto dto) {
        return bookService.save(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BookDto updateBook(@PathVariable("id") Integer bookId, @RequestBody UpdateBookDto dto) {
        return bookService.update(bookId, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable("id") Integer bookId) {
        bookService.delete(bookId);
    }
}
