package ru.diasoft.spring.booklibraryshell.service;

public interface BookService {
    String getBooksInfo();

    String getBooksInfoByGenre(String genreName);

    String getBooksInfoByAuthor(Long authorId);

    String getBookInfo(Long bookId);

    long getNumberOfBooks();

    void deleteBook(Long bookId);

    String addBook(String title, Long authorId, Long genreId);

    String updateBook(Long bookId, String title, Long authorId, Long genreId);
}
