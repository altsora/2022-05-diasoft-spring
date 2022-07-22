package ru.diasoft.spring.booklibrary.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Book;
import ru.diasoft.spring.booklibrary.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тесты репозитория BookCommentRepositoryImpl")
@DataJpaTest
@Import(BookCommentRepositoryImpl.class)
class BookCommentRepositoryImplTest {
    private static final Long FIRST_COMMENT_ID = 1L;
    private static final Long FIRST_BOOK_ID = 1L;
    private static final int EXPECTED_NUMBERS_OF_COMMENTS = 4;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private TestEntityManager em;
    @Autowired
    private BookCommentRepositoryImpl repo;

    @Test
    @DisplayName("Поиск комментария по ID")
    void findByIdTest() {
        final BookComment expectedComment = em.find(BookComment.class, FIRST_COMMENT_ID);
        assertThat(expectedComment).isNotNull();

        final Optional<BookComment> actualComment = repo.findById(FIRST_COMMENT_ID);
        assertThat(actualComment)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
        assertNotNull(actualComment.get().getBook().getId());
    }

    @Test
    @DisplayName("Получить все комментарии книг")
    void findAllTest() {
        final SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        final List<BookComment> comments = repo.findAll();
        assertThat(comments)
                .isNotNull()
                .hasSize(EXPECTED_NUMBERS_OF_COMMENTS)
                .allMatch(c -> c.getId() != null)
                .allMatch(c -> c.getBook().getId() != null)
                .allMatch(c -> c.getBook().getTitle() != null);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
                .isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @Test
    @DisplayName("Удаление комментария по ID")
    void deleteByIdTest() {
        final long countBeforeDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        final BookComment comment = em.find(BookComment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();
        em.detach(comment);

        repo.deleteById(FIRST_COMMENT_ID);

        final long countAfterDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        final BookComment deletedComment = em.find(BookComment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
        assertThat(countAfterDelete).isEqualTo(countBeforeDelete - 1);
    }

    @Test
    @DisplayName("Удаление комментария по объекту")
    void deleteOneTest() {
        final long countBeforeDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        final BookComment comment = em.find(BookComment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();

        repo.deleteOne(comment);

        final long countAfterDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        final BookComment deletedComment = em.find(BookComment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
        assertThat(countAfterDelete).isEqualTo(countBeforeDelete - 1);
    }

    @Test
    @DisplayName("Сохранение нового комментария")
    void saveTest() {
        final long countBeforeSave = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        final Book book = em.find(Book.class, FIRST_BOOK_ID);
        final String text = "comment_text";
        final BookComment newComment = BookComment.builder()
                .book(book)
                .text(text)
                .build();

        final BookComment bookComment = repo.save(newComment);

        final long countAfterSave = em.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM BookComment c", Long.class)
                .getSingleResult();
        assertThat(countAfterSave).isEqualTo(countBeforeSave + 1);
        assertNotNull(bookComment);
        assertNotNull(bookComment.getId());
        assertEquals(text, bookComment.getText());
        assertEquals(FIRST_BOOK_ID, bookComment.getBook().getId());
    }
}