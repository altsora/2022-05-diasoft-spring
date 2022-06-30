package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibrary.service.AuthorService;
import ru.diasoft.spring.booklibrary.util.Commands;

import java.util.Objects;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorService authorService;

    @ShellMethod(key = Commands.COMMAND_FIND_AUTHOR, value = "Поиск авторов")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(defaultValue = Commands.NONE_VALUE) Long authorId) {
        log.info("allKey = [{}], authorId = [{}]", allKey, authorId);
        if (!allKey && Objects.isNull(authorId))
            return "Что ищем?";

        if (allKey)
            return authorService.getAuthorsInfo();

        return authorService.getAuthorInfo(authorId);
    }
}