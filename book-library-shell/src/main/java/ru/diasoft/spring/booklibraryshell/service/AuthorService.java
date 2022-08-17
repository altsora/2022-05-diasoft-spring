package ru.diasoft.spring.booklibraryshell.service;

import ru.diasoft.spring.booklibraryshell.domain.Author;

public interface AuthorService {
    String getAuthorsInfo();

    String getAuthorInfo(Long authorId);

    String getFullName(Author author);
}
