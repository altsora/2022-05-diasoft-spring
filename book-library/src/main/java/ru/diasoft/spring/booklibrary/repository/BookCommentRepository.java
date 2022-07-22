package ru.diasoft.spring.booklibrary.repository;

import ru.diasoft.spring.booklibrary.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    Optional<BookComment> findById(Long id);

    List<BookComment> findAll();

    BookComment save(BookComment bookComment);

    void deleteById(Long id);

    void deleteOne(BookComment bookComment);
}
