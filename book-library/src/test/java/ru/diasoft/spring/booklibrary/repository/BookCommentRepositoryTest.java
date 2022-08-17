package ru.diasoft.spring.booklibrary.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.booklibrary.domain.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты репозитория BookCommentRepository")
@DataJpaTest
class BookCommentRepositoryTest {
    private static final int EXPECTED_NUMBERS_OF_COMMENTS = 4;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    @AfterEach
    void tearDown() {
        em.getEntityManager()
                .getEntityManagerFactory()
                .unwrap(SessionFactory.class)
                .getStatistics()
                .clear();
    }

    @Test
    @DisplayName("Получить все комментарии книг")
    void findAllTest() {
        final SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        final List<BookComment> comments = bookCommentRepository.findAll();
        assertThat(comments)
                .isNotNull()
                .hasSize(EXPECTED_NUMBERS_OF_COMMENTS)
                .allMatch(c -> c.getId() != null)
                .allMatch(c -> c.getBook().getId() != null)
                .allMatch(c -> c.getBook().getTitle() != null);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
                .isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}