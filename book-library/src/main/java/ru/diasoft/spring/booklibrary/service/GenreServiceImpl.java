package ru.diasoft.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.booklibrary.domain.Genre;
import ru.diasoft.spring.booklibrary.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public String getGenresInfo() {
        final StringBuilder sb = new StringBuilder("Жанры:\n");
        final List<Genre> genres = genreRepository.findAll();
        for (int i = 0; i < genres.size(); i++) {
            final Genre genre = genres.get(i);
            final String row = String.format("%d. ID: %d, название: %s",
                    (i + 1), genre.getId(), genre.getName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getGenreInfo(Long genreId) {
        final Optional<Genre> genreOpt = genreRepository.findById(genreId);
        if (genreOpt.isPresent()) {
            return String.format("Жанр найден\nID: %d, название: %s", genreId, genreOpt.get().getName());
        }

        return "Жанр с ID " + genreId + " не найден";
    }
}
