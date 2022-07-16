package ru.diasoft.spring.booklibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты репозитория GenreRepositoryImpl")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {
    private static final Long FIRST_GENRE_ID = 1L;
    private static final Long SECOND_GENRE_ID = 2L;

    @Autowired
    private GenreRepositoryImpl genreRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Поиск жанра по ID (найден и не найден)")
    void findByIdTest() {
        final Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        final Optional<Genre> actualExistingGenreOpt = genreRepository.findById(FIRST_GENRE_ID);
        assertThat(actualExistingGenreOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);

        final Optional<Genre> actualNonExistingGenreOpt = genreRepository.findById(-1L);
        assertThat(actualNonExistingGenreOpt).isEmpty();
    }

    @Test
    @DisplayName("Получить все жанры")
    void findAll() {
        final Genre genre1 = em.find(Genre.class, FIRST_GENRE_ID);
        final Genre genre2 = em.find(Genre.class, SECOND_GENRE_ID);

        final List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres)
                .isNotNull()
                .hasSize(2)
                .contains(genre1, genre2);
    }
}