package ru.diasoft.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Тесты класса AuthorDaoJdbcTest")
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final long AUTHOR_ID_EXPECTED = 1L;
    private static final String FIRST_NAME_EXPECTED = "Domenic";
    private static final String LAST_NAME_EXPECTED = "Dickson";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    @DisplayName("Найти автора по ID: автор найден")
    void findById_exists() {
        final Optional<Author> actual = authorDao.findById(1L);
        assertTrue(actual.isPresent());
        final Author author = actual.get();
        assertEquals(AUTHOR_ID_EXPECTED, author.getId());
        assertEquals(FIRST_NAME_EXPECTED, author.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, author.getLastName());
    }

    @Test
    @DisplayName("Найти автора по ID: автор не найден")
    void findById_notExists() {
        final Optional<Author> actual = authorDao.findById(-1L);
        assertFalse(actual.isPresent());
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findAllTest() {
        final Author expectedAuthor = Author.builder()
                .id(AUTHOR_ID_EXPECTED)
                .firstName(FIRST_NAME_EXPECTED)
                .lastName(LAST_NAME_EXPECTED)
                .build();
        final List<Author> actualList = authorDao.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(10);
        final Author actualAuthor = actualList.get(0);
        assertEquals(expectedAuthor, actualAuthor);
    }
}