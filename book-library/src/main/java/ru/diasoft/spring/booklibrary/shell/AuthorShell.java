package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibrary.dao.AuthorDao;
import ru.diasoft.spring.booklibrary.domain.Author;
import ru.diasoft.spring.booklibrary.util.Commands;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorDao authorDao;

    @ShellMethod(key = Commands.COMMAND_FIND_AUTHOR, value = "Поиск авторов")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(defaultValue = Commands.NONE_VALUE) Long authorId) {
        log.info("allKey = [{}], authorId = [{}]", allKey, authorId);
        if (!allKey && Objects.isNull(authorId))
            return "Что ищем?";

        if (allKey) {
            final StringBuilder sb = new StringBuilder("Авторы:\n");
            final List<Author> authors = authorDao.findAll();
            for (int i = 0; i < authors.size(); i++) {
                final Author author = authors.get(i);
                final String row = String.format("%d. ID: %d, фамилия: %s, имя: %s",
                        (i + 1), author.getId(), author.getLastName(), author.getFirstName());
                sb.append(row).append("\n");
            }
            return sb.toString();
        }

        final Optional<Author> authorOpt = authorDao.findById(authorId);
        if (authorOpt.isPresent()) {
            final Author author = authorOpt.get();
            return String.format("Автор найден\nID: %d, имя: %s, фамилия: %s",
                    authorId, author.getFirstName(), author.getLastName());
        }

        return "Автор с ID " + authorId + " не найден";
    }
}