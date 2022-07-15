package ru.diasoft.spring.booklibrary.service;

import ru.diasoft.spring.booklibrary.domain.Author;

public interface AuthorService {
    String getAuthorsInfo();

    String getAuthorInfo(Long authorId);

    String getFullName(Author author);
}
