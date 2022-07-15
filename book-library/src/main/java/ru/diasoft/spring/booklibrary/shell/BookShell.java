package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibrary.service.BookService;
import ru.diasoft.spring.booklibrary.util.Commands;

import java.util.Objects;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookService bookService;

    @ShellMethod(key = Commands.COMMAND_FIND_BOOK, value = "Поиск книг")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(value = "--id", defaultValue = Commands.NONE_VALUE) Long bookId,
                       @ShellOption(value = "--genre", defaultValue = Commands.NONE_VALUE) String genreName,
                       @ShellOption(value = "--authorId", defaultValue = Commands.NONE_VALUE) Long authorId) {
        log.info("allKey = {}, bookId = {}, genreName = {}, authorId = {}", allKey, bookId, genreName, authorId);
        if (!allKey && Objects.isNull(bookId) && Objects.isNull(genreName) && Objects.isNull(authorId))
            return "Что ищем?";

        if (allKey)
            return bookService.getBooksInfo();


        if (Objects.nonNull(bookId))
            return bookService.getBookInfo(bookId);


        if (Objects.nonNull(genreName))
            return bookService.getBooksInfoByGenre(genreName);

        if (Objects.nonNull(authorId))
            return bookService.getBooksInfoByAuthor(authorId);

        throw new IllegalStateException("Недостижимое состояние");
    }

    @ShellMethod(key = Commands.COMMAND_COUNT_BOOK, value = "Выводит общее количество книг")
    public String count() {
        return "Всего книг: " + bookService.getNumberOfBooks();
    }

    @ShellMethod(key = Commands.COMMAND_DELETE_BOOK, value = "Удаление книги по ID")
    public String delete(@ShellOption(value = "--id") Long bookId) {
        bookService.deleteBook(bookId);
        return "Книга с ID " + bookId + " удалена";
    }

    @ShellMethod(key = Commands.COMMAND_ADD_BOOK, value = "Добавление книги")
    public String addBook(@ShellOption(value = {"--title", "-t"}) String title,
                          @ShellOption(value = {"--authorId", "-a"}) Long authorId,
                          @ShellOption(value = {"--genreId", "-g"}) Long genreId) {
        return bookService.addBook(title, authorId, genreId);
    }

    @ShellMethod(key = Commands.COMMAND_UPDATE_BOOK, value = "Обновление книги")
    public String updateBook(@ShellOption(value = {"--id", "-id"}) Long bookId,
                             @ShellOption(value = {"--title", "-t"}) String title,
                             @ShellOption(value = {"--authorId", "-a"}) Long authorId,
                             @ShellOption(value = {"--genreId", "-g"}) Long genreId) {
        return bookService.updateBook(bookId, title, authorId, genreId);
    }
}
