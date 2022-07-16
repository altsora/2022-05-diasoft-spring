package ru.diasoft.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.booklibrary.domain.Author;
import ru.diasoft.spring.booklibrary.domain.Book;
import ru.diasoft.spring.booklibrary.domain.Genre;
import ru.diasoft.spring.booklibrary.repository.AuthorRepository;
import ru.diasoft.spring.booklibrary.repository.BookRepository;
import ru.diasoft.spring.booklibrary.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private final AuthorService authorService;

    @Override
    public String getBooksInfo() {
        final List<Book> books = bookRepository.findAll();
        return getBookList(books);
    }

    @Override
    public String getBooksInfoByGenre(String genreName) {
        final List<Book> books = bookRepository.findAllByGenreName(genreName);
        return getBookList(books);
    }

    @Override
    public String getBooksInfoByAuthor(Long authorId) {
        final List<Book> books = bookRepository.findAllByAuthorId(authorId);
        return getBookList(books);
    }

    private String getBookList(List<Book> books) {
        final StringBuilder sb = new StringBuilder("Книги:\n");
        for (int i = 0; i < books.size(); i++) {
            final Book book = books.get(i);
            final Author author = book.getAuthor();
            final Genre genre = book.getGenre();

            final String row = String.format("%d. ID: %d, название: %s, автор: %s, жанр: %s",
                    (i + 1),
                    book.getId(),
                    book.getTitle(),
                    authorService.getFullName(author),
                    genre.getName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getBookInfo(Long bookId) {
        final Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            final Book book = bookOpt.get();
            final Author author = book.getAuthor();
            final Genre genre = book.getGenre();

            return String.format("ID: %d, название: %s, автор: %s, жанр: %s",
                    bookId,
                    book.getTitle(),
                    authorService.getFullName(author),
                    genre.getName());
        }
        return "Книга с ID " + bookId + " не найдена";
    }

    @Override
    public long getNumberOfBooks() {
        return bookRepository.count();
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public String addBook(String title, Long authorId, Long genreId) {
        final Optional<Author> authorOpt = authorRepository.findById(authorId);
        if (authorOpt.isEmpty())
            return "Автор с ID " + authorId + " не найден";

        final Optional<Genre> genreOpt = genreRepository.findById(genreId);
        if (genreOpt.isEmpty())
            return "Жанр с ID " + genreId + " не найден";

        final Book book = Book.builder()
                .title(title)
                .author(authorOpt.get())
                .genre(genreOpt.get())
                .build();
        final Long bookId = bookRepository.saveOrUpdate(book);
        return "Книга успешно добавлено с ID " + bookId;
    }

    @Override
    public String updateBook(Long bookId, String title, Long authorId, Long genreId) {
        final Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty())
            return "Книга с ID " + bookId + " не найдена";

        final Optional<Author> authorOpt = authorRepository.findById(authorId);
        if (authorOpt.isEmpty())
            return "Автор с ID " + authorId + " не найден";

        final Optional<Genre> genreOpt = genreRepository.findById(genreId);
        if (genreOpt.isEmpty())
            return "Жанр с ID " + genreId + " не найден";

        final Book book = bookOpt.get()
                .setTitle(title)
                .setAuthor(authorOpt.get())
                .setGenre(genreOpt.get());
        bookRepository.saveOrUpdate(book);
        return "Книга с ID " + bookId + " успешно обновлена";
    }
}
