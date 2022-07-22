package ru.diasoft.spring.booklibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты репозитория AuthorRepositoryImpl")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final Long SECOND_AUTHOR_ID = 2L;

    @Autowired
    private AuthorRepositoryImpl authorRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Поиск автора по ID (найден и не найден)")
    void findByIdTest() {
        final Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        final Optional<Author> actualExistingAuthorOpt = authorRepository.findById(FIRST_AUTHOR_ID);
        assertThat(actualExistingAuthorOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);

        final Optional<Author> actualNonExistingAuthorOpt = authorRepository.findById(-1L);
        assertThat(actualNonExistingAuthorOpt).isEmpty();
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findAll() {
        final Author author1 = em.find(Author.class, FIRST_AUTHOR_ID);
        final Author author2 = em.find(Author.class, SECOND_AUTHOR_ID);

        final List<Author> actualBooks = authorRepository.findAll();
        assertThat(actualBooks)
                .isNotNull()
                .hasSize(2)
                .contains(author1, author2);
    }
}