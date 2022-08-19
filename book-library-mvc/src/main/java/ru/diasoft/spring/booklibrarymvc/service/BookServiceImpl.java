package ru.diasoft.spring.booklibrarymvc.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.booklibrarymvc.domain.Author;
import ru.diasoft.spring.booklibrarymvc.domain.Book;
import ru.diasoft.spring.booklibrarymvc.domain.Genre;
import ru.diasoft.spring.booklibrarymvc.exception.DomainNotFoundException;
import ru.diasoft.spring.booklibrarymvc.mapper.Mapper;
import ru.diasoft.spring.booklibrarymvc.model.AddBookDto;
import ru.diasoft.spring.booklibrarymvc.model.BookDto;
import ru.diasoft.spring.booklibrarymvc.model.UpdateBookDto;
import ru.diasoft.spring.booklibrarymvc.repository.AuthorRepository;
import ru.diasoft.spring.booklibrarymvc.repository.BookRepository;
import ru.diasoft.spring.booklibrarymvc.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(Mapper::toDto)
                .orElseThrow(() -> DomainNotFoundException.id(Book.class, bookId));
    }

    @Override
    @Transactional
    public BookDto addBook(AddBookDto dto) {
        final Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> DomainNotFoundException.id(Author.class, dto.getAuthorId()));
        final Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> DomainNotFoundException.id(Genre.class, dto.getGenreId()));
        final Book newBook = Book.builder()
                .title(dto.getTitle())
                .author(author)
                .genre(genre)
                .build();
        final Book savedBook = bookRepository.saveAndFlush(newBook);
        return Mapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(Long bookId, UpdateBookDto dto) {
        final Book currentBook = bookRepository.findById(bookId)
                .orElseThrow(() -> DomainNotFoundException.id(Book.class, bookId));

        currentBook.setTitle(dto.getTitle());
        if (!currentBook.getAuthor().getId().equals(dto.getAuthorId())) {
            final Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> DomainNotFoundException.id(Author.class, dto.getAuthorId()));
            currentBook.setAuthor(author);
        }
        if (!currentBook.getGenre().getId().equals(dto.getGenreId())) {
            final Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> DomainNotFoundException.id(Genre.class, dto.getGenreId()));
            currentBook.setGenre(genre);
        }

        final Book savedBook = bookRepository.saveAndFlush(currentBook);
        return Mapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
