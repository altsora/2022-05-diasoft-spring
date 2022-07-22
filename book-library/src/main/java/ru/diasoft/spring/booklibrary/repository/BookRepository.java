package ru.diasoft.spring.booklibrary.repository;

import ru.diasoft.spring.booklibrary.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Long saveOrUpdate(Book book);

    void deleteById(Long id);

    long count();

    List<Book> findAllByGenreName(String genreName);

    List<Book> findAllByAuthorId(Long authorId);
}
