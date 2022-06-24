package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.booklibrary.dao.GenreDao;
import ru.diasoft.spring.booklibrary.domain.Genre;
import ru.diasoft.spring.booklibrary.util.Commands;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreDao genreDao;

    @ShellMethod(key = Commands.COMMAND_FIND_GENRE, value = "Поиск жанров")
    public String find(@ShellOption(Commands.KEY_ALL) boolean allKey,
                       @ShellOption(defaultValue = Commands.NONE_VALUE) Long genreId) {
        log.info("allKey = [{}], genreId = [{}]", allKey, genreId);
        if (!allKey && Objects.isNull(genreId))
            return "Что ищем?";

        if (allKey) {
            final StringBuilder sb = new StringBuilder("Жанры:\n");
            final List<Genre> genres = genreDao.findAll();
            for (int i = 0; i < genres.size(); i++) {
                final Genre genre = genres.get(i);
                final String row = String.format("%d. ID: %d, название: %s",
                        (i + 1), genre.getId(), genre.getName());
                sb.append(row).append("\n");
            }
            return sb.toString();
        }

        final Optional<Genre> genreOpt = genreDao.findById(genreId);
        if (genreOpt.isPresent()) {
            return String.format("Жанр найден\nID: %d, название: %s", genreId, genreOpt.get().getName());
        }

        return "Жанр с ID " + genreId + " не найден";
    }
}
