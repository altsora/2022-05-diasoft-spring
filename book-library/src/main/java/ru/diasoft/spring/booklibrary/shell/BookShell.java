package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibrary.dao.AuthorDao;
import ru.diasoft.spring.booklibrary.dao.BookDao;
import ru.diasoft.spring.booklibrary.dao.GenreDao;
import ru.diasoft.spring.booklibrary.domain.Author;
import ru.diasoft.spring.booklibrary.domain.Book;
import ru.diasoft.spring.booklibrary.domain.Genre;
import ru.diasoft.spring.booklibrary.util.Commands;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @ShellMethod(key = Commands.COMMAND_FIND_BOOK, value = "Поиск книг")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(value = "--id", defaultValue = Commands.NONE_VALUE) Long bookId,
                       @ShellOption(value = "--genre", defaultValue = Commands.NONE_VALUE) String genreName,
                       @ShellOption(value = "--authorId", defaultValue = Commands.NONE_VALUE) Long authorId) {
        log.info("allKey = {}, bookId = {}, genreName = {}, authorId = {}", allKey, bookId, genreName, authorId);
        if (!allKey && Objects.isNull(bookId) && Objects.isNull(genreName) && Objects.isNull(authorId))
            return "Что ищем?";

        if (allKey) {
            final List<Book> books = bookDao.findAll();
            return getBookList(books);
        }

        if (Objects.nonNull(bookId)) {
            final Optional<Book> bookOpt = bookDao.findById(bookId);
            if (bookOpt.isPresent()) {
                final Book book = bookOpt.get();
                final Author author = authorDao.findById(book.getAuthorId()).orElseThrow();
                final Genre genre = genreDao.findById(book.getGenreId()).orElseThrow();

                return String.format("ID: %d, название: %s, автор: %s, жанр: %s",
                        bookId,
                        book.getTitle(),
                        author.getFullName(),
                        genre.getName());
            }
            return "Книга с ID " + bookId + " не найдена";
        }

        if (Objects.nonNull(genreName)) {
            final List<Book> books = bookDao.findAllByGenre(genreName);
            return getBookList(books);
        }

        if (Objects.nonNull(authorId)) {
            final List<Book> books = bookDao.findAllByAuthor(authorId);
            return getBookList(books);
        }

        throw new IllegalStateException("Недостижимое состояние");
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
                    author.getFullName(),
                    genre.getName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(key = Commands.COMMAND_COUNT_BOOK, value = "Выводит общее количество книг")
    public String count() {
        return "Всего книг: " + bookDao.count();
    }

    @ShellMethod(key = Commands.COMMAND_DELETE_BOOK, value = "Удаление книги по ID")
    public String delete(@ShellOption(value = "--id") Long bookId) {
        bookDao.delete(bookId);
        return "Книга с ID " + bookId + " удалена";
    }

    @ShellMethod(key = Commands.COMMAND_ADD_BOOK, value = "Добавление книги")
    public String addBook(@ShellOption(value = {"--title", "-t"}) String title,
                          @ShellOption(value = {"--authorId", "-a"}) Long authorId,
                          @ShellOption(value = {"--genreId", "-g"}) Long genreId) {
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

    @ShellMethod(key = Commands.COMMAND_UPDATE_BOOK, value = "Обновление книги")
    public String updateBook(@ShellOption(value = {"--id", "-id"}) Long bookId,
                             @ShellOption(value = {"--title", "-t"}) String title,
                             @ShellOption(value = {"--authorId", "-a"}) Long authorId,
                             @ShellOption(value = {"--genreId", "-g"}) Long genreId) {
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
