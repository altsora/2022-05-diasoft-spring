package ru.diasoft.spring.booklibrary.repository;

import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(Long id);
}
