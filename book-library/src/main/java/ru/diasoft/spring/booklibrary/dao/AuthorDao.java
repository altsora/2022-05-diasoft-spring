package ru.diasoft.spring.booklibrary.dao;

import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();

    Optional<Author> findById(Long id);
}