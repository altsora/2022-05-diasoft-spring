package ru.diasoft.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Тесты класса BookDaoJdbcTest")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    public static final Long BOOK_ID_EXPECTED = 1L;
    public static final String BOOK_TITLE_EXPECTED = "Great Expectations";
    public static final Long AUTHOR_ID_EXPECTED = 6L;
    public static final Long GENRE_ID_EXPECTED = 6L;
    public static final String GENRE_NAME_EXPECTED = "Romance";
    public static final int BOOKS_COUNT_EXPECTED = 10;

    @Autowired
    private BookDaoJdbc dao;

    @Test
    @DisplayName("Найти книгу по ID: книга найдена")
    void findById_existsTest() {
        final Optional<Book> actual = dao.findById(BOOK_ID_EXPECTED);
        assertTrue(actual.isPresent());
        final Book book = actual.get();
        assertEquals(BOOK_ID_EXPECTED, book.getId());
        assertEquals(BOOK_TITLE_EXPECTED, book.getTitle());
        assertEquals(AUTHOR_ID_EXPECTED, book.getAuthorId());
        assertEquals(GENRE_ID_EXPECTED, book.getGenreId());
    }

    @Test
    @DisplayName("Найти книгу по ID: книга не найдена")
    void findById_notExistsTest() {
        final Optional<Book> actual = dao.findById(-1L);
        assertFalse(actual.isPresent());
    }

    @Test
    @DisplayName("Получить общее количество книг")
    void countTest() {
        final int actual = dao.count();
        assertEquals(BOOKS_COUNT_EXPECTED, actual);
    }

    @Test
    @DisplayName("Получить книги по названию жанра: книги существуют")
    void findALlByGenreTest() {
        final List<Book> actualList = dao.findAllByGenre(GENRE_NAME_EXPECTED);
        assertThat(actualList)
                .isNotNull()
                .hasSize(4);
        actualList.forEach(b -> assertEquals(GENRE_ID_EXPECTED, b.getGenreId()));
    }

    @Test
    @DisplayName("Получить книги по названию жанра: книги не существуют")
    void findALlByGenre_notFoundTest() {
        final List<Book> actualList = dao.findAllByGenre("UNKNOWN_GENRE");
        assertThat(actualList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Получить книги по ID автора: книги существуют")
    void findAllByAuthorTest() {
        final List<Book> actualList = dao.findAllByAuthor(AUTHOR_ID_EXPECTED);
        assertThat(actualList)
                .isNotNull()
                .hasSize(2);
        actualList.forEach(b -> assertEquals(AUTHOR_ID_EXPECTED, b.getAuthorId()));
    }

    @Test
    @DisplayName("Получить книги по ID автора: книги существуют")
    void findAllByAuthor_notFoundTest() {
        final List<Book> actualList = dao.findAllByAuthor(-1L);
        assertThat(actualList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Сохранение новой книги: успешно")
    void saveTest() {
        final Book newBook = Book.builder()
                .title("new_book_test")
                .authorId(AUTHOR_ID_EXPECTED)
                .genreId(GENRE_ID_EXPECTED)
                .build();
        final int countBeforeSave = dao.count();
        final Long newBookId = dao.save(newBook);
        final int countAfterSave = dao.count();
        assertEquals(countAfterSave, countBeforeSave + 1);
        assertTrue(dao.findById(newBookId).isPresent());
    }

    @Test
    @DisplayName("Обновление книги: успешно")
    void updateTest() {
        final Optional<Book> bookBeforeUpdateOpt = dao.findById(BOOK_ID_EXPECTED);
        assertTrue(bookBeforeUpdateOpt.isPresent());
        final Book bookBeforeUpdate = bookBeforeUpdateOpt.get();
        bookBeforeUpdate.setTitle("new_title");

        dao.update(bookBeforeUpdate);
        final Optional<Book> bookAfterUpdateOpt = dao.findById(BOOK_ID_EXPECTED);
        assertTrue(bookAfterUpdateOpt.isPresent());
        final Book bookAfterUpdate = bookAfterUpdateOpt.get();
        assertEquals(bookBeforeUpdate.getId(), bookAfterUpdate.getId());
        assertEquals("new_title", bookAfterUpdate.getTitle());
        assertEquals(bookBeforeUpdate.getAuthorId(), bookAfterUpdate.getAuthorId());
        assertEquals(bookBeforeUpdate.getGenreId(), bookAfterUpdate.getGenreId());
    }

    @Test
    @DisplayName("Удаление книги: успешно")
    void deleteTest() {
        final Optional<Book> bookBeforeDeleteOpt = dao.findById(BOOK_ID_EXPECTED);
        assertTrue(bookBeforeDeleteOpt.isPresent());

        final int countBeforeDelete = dao.count();
        dao.delete(BOOK_ID_EXPECTED);
        final int countAfterDelete = dao.count();

        final Optional<Book> bookAfterDeleteOpt = dao.findById(BOOK_ID_EXPECTED);
        assertFalse(bookAfterDeleteOpt.isPresent());
        assertEquals(countAfterDelete, countBeforeDelete - 1);
    }
}