package ru.diasoft.spring.jwtmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.jwtmvc.domain.Book;
import ru.diasoft.spring.jwtmvc.model.AddBookDto;
import ru.diasoft.spring.jwtmvc.model.BookDto;
import ru.diasoft.spring.jwtmvc.model.UpdateBookDto;
import ru.diasoft.spring.jwtmvc.repository.BookRepository;
import ru.diasoft.spring.jwtmvc.utils.Mapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(Mapper::fromDomainToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(Mapper::fromDomainToDto)
                .orElseThrow(() -> new EntityNotFoundException("Книга с ID " + bookId + " не найдена"));
    }

    @Override
    @Transactional
    public BookDto save(AddBookDto dto) {
        final Book book = Book.builder().title(dto.getTitle()).build();
        final Book savedBook = bookRepository.save(book);
        return Mapper.fromDomainToDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(Integer bookId, UpdateBookDto dto) {
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Книга с ID " + bookId + " не найдена"));
        book.setTitle(dto.getTitle());
        final Book savedBook = bookRepository.save(book);
        return Mapper.fromDomainToDto(savedBook);
    }

    @Override
    @Transactional
    public void delete(Integer bookId) {
        bookRepository.deleteById(bookId);
    }
}
