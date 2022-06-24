package ru.diasoft.spring.booklibrary.dao;

import ru.diasoft.spring.booklibrary.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> findAllByGenre(String genreName);

    List<Book> findAllByAuthor(Long authorId);

    Long save(Book book);

    void delete(Long id);

    void update(Book book);

    int count();
}