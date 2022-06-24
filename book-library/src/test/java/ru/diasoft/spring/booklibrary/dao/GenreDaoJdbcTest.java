package ru.diasoft.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Тесты класса GenreDaoJdbcTest")
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final Long GENRE_ID_EXPECTED = 1L;
    private static final String GENRE_NAME_EXPECTED = "History";

    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("Найти жанр по ID: жанр найден")
    void findById_exists() {
        final Optional<Genre> actual = genreDao.findById(1L);
        assertTrue(actual.isPresent());
        final Genre genre = actual.get();
        assertEquals(GENRE_ID_EXPECTED, genre.getId());
        assertEquals(GENRE_NAME_EXPECTED, genre.getName());
    }

    @Test
    @DisplayName("Найти жанр по ID: жанр не найден")
    void findById_notExists() {
        final Optional<Genre> actual = genreDao.findById(-1L);
        assertFalse(actual.isPresent());
    }

    @Test
    @DisplayName("Получить все жанры")
    void findAllTest() {
        final Genre expectedGenre = Genre.builder()
                .id(GENRE_ID_EXPECTED)
                .name(GENRE_NAME_EXPECTED)
                .build();
        final List<Genre> actualList = genreDao.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(10);
        final Genre actualGenre = actualList.get(0);
        assertEquals(expectedGenre, actualGenre);
    }
}