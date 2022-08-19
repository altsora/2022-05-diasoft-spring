package ru.diasoft.spring.booklibrarymvc.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.booklibrarymvc.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты репозитория BookRepository")
@DataJpaTest
class BookRepositoryTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    void tearDown() {
        em.getEntityManager()
                .getEntityManagerFactory()
                .unwrap(SessionFactory.class)
                .getStatistics()
                .clear();
    }

    @Test
    @DisplayName("Получить все книги + проверка количества запросов")
    void findAllTest() {
        //Способы решения N+1:
        // 1. Граф (указывается при запросе и над сущностью)
        // 2. join fetch прямо в запросе
        // 3. @Fetch(SUBSELECT) + @BatchSize(N) над полем сущности - для N родителей будет запрос дочки
        final SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        final List<Book> books = bookRepository.findAll();
        assertThat(books)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> b.getTitle() != null)
                .allMatch(b -> b.getAuthor() != null && b.getAuthor().getFirstName() != null)
                .allMatch(b -> b.getGenre() != null && b.getGenre().getName() != null)
                .allMatch(b -> b.getComments() != null)
                .anyMatch(b -> b.getComments().size() == 3);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
                .isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}