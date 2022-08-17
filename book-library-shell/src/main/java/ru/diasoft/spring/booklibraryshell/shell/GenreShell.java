package ru.diasoft.spring.booklibraryshell.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibraryshell.service.GenreService;
import ru.diasoft.spring.booklibraryshell.util.Commands;

import java.util.Objects;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreService genreService;

    @ShellMethod(key = Commands.COMMAND_FIND_GENRE, value = "Поиск жанров")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(defaultValue = Commands.NONE_VALUE) Long genreId) {
        log.info("allKey = [{}], genreId = [{}]", allKey, genreId);
        if (!allKey && Objects.isNull(genreId))
            return "Что ищем?";

        if (allKey)
            return genreService.getGenresInfo();

        return genreService.getGenreInfo(genreId);
    }
}
