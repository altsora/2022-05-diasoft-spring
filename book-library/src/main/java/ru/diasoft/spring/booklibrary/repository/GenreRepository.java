package ru.diasoft.spring.booklibrary.repository;

import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);
}
