package ru.diasoft.spring.booklibrary.dao;

import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);
}
