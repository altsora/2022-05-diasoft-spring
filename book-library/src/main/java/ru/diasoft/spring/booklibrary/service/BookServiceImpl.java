package ru.diasoft.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.booklibrary.dao.AuthorDao;
import ru.diasoft.spring.booklibrary.dao.BookDao;
import ru.diasoft.spring.booklibrary.dao.GenreDao;
import ru.diasoft.spring.booklibrary.domain.Author;
import ru.diasoft.spring.booklibrary.domain.Book;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public String getBooksInfo() {
        final List<Book> books = bookDao.findAll();
        return getBookList(books);
    }

    @Override
    public String getBooksInfoByGenre(String genreName) {
        final List<Book> books = bookDao.findAllByGenre(genreName);
        return getBookList(books);
    }

    @Override
    public String getBooksInfoByAuthor(Long authorId) {
        final List<Book> books = bookDao.findAllByAuthor(authorId);
        return getBookList(books);
    }

    private String getBookList(List<Book> books) {
        final StringBuilder sb = new StringBuilder("Книги:\n");
        for (int i = 0; i < books.size(); i++) {
            final Book book = books.get(i);
            final Author author = authorDao.findById(book.getAuthorId()).orElseThrow();
            final Genre genre = genreDao.findById(book.getGenreId()).orElseThrow();

            final String row = String.format("%d. ID: %d, название: %s, автор: %s, жанр: %s",
                    (i + 1),
                    book.getId(),
                    book.getTitle(),
                    authorDao.getFullName(author),
                    genre.getName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getBookInfo(Long bookId) {
        final Optional<Book> bookOpt = bookDao.findById(bookId);
        if (bookOpt.isPresent()) {
            final Book book = bookOpt.get();
            final Author author = authorDao.findById(book.getAuthorId()).orElseThrow();
            final Genre genre = genreDao.findById(book.getGenreId()).orElseThrow();

            return String.format("ID: %d, название: %s, автор: %s, жанр: %s",
                    bookId,
                    book.getTitle(),
                    authorDao.getFullName(author),
                    genre.getName());
        }
        return "Книга с ID " + bookId + " не найдена";
    }

    @Override
    public int getNumberOfBooks() {
        return bookDao.count();
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDao.delete(bookId);
    }

    @Override
    public String addBook(String title, Long authorId, Long genreId) {
        final Optional<Author> authorOpt = authorDao.findById(authorId);
        if (authorOpt.isEmpty())
            return "Автор с ID " + authorId + " не найден";

        final Optional<Genre> genreOpt = genreDao.findById(genreId);
        if (genreOpt.isEmpty())
            return "Жанр с ID " + genreId + " не найден";

        final Book book = Book.builder()
                .title(title)
                .authorId(authorId)
                .genreId(genreId)
                .build();
        final Long bookId = bookDao.save(book);
        return "Книга успешно добавлено с ID " + bookId;
    }

    @Override
    public String updateBook(Long bookId, String title, Long authorId, Long genreId) {
        final Optional<Book> bookOpt = bookDao.findById(bookId);
        if (bookOpt.isEmpty())
            return "Книга с ID " + bookId + " не найдена";

        final Optional<Author> authorOpt = authorDao.findById(authorId);
        if (authorOpt.isEmpty())
            return "Автор с ID " + authorId + " не найден";

        final Optional<Genre> genreOpt = genreDao.findById(genreId);
        if (genreOpt.isEmpty())
            return "Жанр с ID " + genreId + " не найден";

        final Book book = bookOpt.get()
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenreId(genreId);
        bookDao.update(book);
        return "Книга с ID " + bookId + " успешно обновлена";
    }
}
