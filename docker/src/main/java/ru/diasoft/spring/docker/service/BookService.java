package ru.diasoft.spring.docker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.docker.domain.Book;
import ru.diasoft.spring.docker.model.AddBookDto;
import ru.diasoft.spring.docker.model.BookDto;
import ru.diasoft.spring.docker.repository.BookRepository;
import ru.diasoft.spring.docker.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDto addBook(AddBookDto dto) {
        final Book book = Book.builder()
                .title(dto.getTitle())
                .build();
        final Book saved = bookRepository.save(book);
        return Mapper.toDto(saved);
    }
}
