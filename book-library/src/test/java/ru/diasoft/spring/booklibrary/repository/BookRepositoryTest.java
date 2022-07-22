package ru.diasoft.spring.booklibrary.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.booklibrary.domain.Author;
import ru.diasoft.spring.booklibrary.domain.Book;
import ru.diasoft.spring.booklibrary.domain.BookComment;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тесты репозитория BookRepository")
@DataJpaTest
class BookRepositoryTest {
    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long AUTHOR_ID = 1L;
    private static final Long GENRE_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXPECTED_QUERIES_COUNT = 2;
    private static final String SECOND_GENRE_NAME = "genre_2";

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
    @DisplayName("Найти книгу по ID (найдена и не найдена)")
    void findBookById() {
        final Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        final Optional<Book> actualBookOpt = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(actualBookOpt)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

        final Optional<Book> actualNonExistingBookOpt = bookRepository.findById(-1L);
        assertThat(actualNonExistingBookOpt).isEmpty();
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

    @Test
    @DisplayName("Получить количество книг")
    void getCountTest() {
        final long actualCount = bookRepository.count();
        assertEquals(EXPECTED_NUMBER_OF_BOOKS, actualCount);
    }

    @Test
    @DisplayName("Сохранение книги")
    void saveBookTest() {
        final Author author = em.find(Author.class, AUTHOR_ID);
        final Genre genre = em.find(Genre.class, GENRE_ID);

        final Book bookBeforeSave = Book.builder()
                .title("new_book")
                .author(author)
                .genre(genre)
                .build();
        final long countBeforeSave = bookRepository.count();
        final Long bookId = bookRepository.save(bookBeforeSave).getId();
        final long countAfterSave = bookRepository.count();
        assertNotNull(bookId);
        assertEquals(countAfterSave, countBeforeSave + 1);

        final Book bookAfterSave = em.find(Book.class, bookId);
        assertNotNull(bookAfterSave);
        assertEquals(bookBeforeSave.getTitle(), bookAfterSave.getTitle());
        assertEquals(author, bookAfterSave.getAuthor());
        assertEquals(genre, bookAfterSave.getGenre());
    }

    @Test
    @DisplayName("Обновление книги")
    void updateBookTest() {
        final Book book = em.find(Book.class, FIRST_BOOK_ID);
        final String newTitle = "newTitle";
        final String oldTitle = book.getTitle();
        book.setTitle(newTitle);

        final long countBeforeSave = bookRepository.count();
        final Long bookId = bookRepository.save(book).getId();
        final long countAfterSave = bookRepository.count();
        final Book actualBook = em.find(Book.class, FIRST_BOOK_ID);

        assertEquals(countBeforeSave, countAfterSave);
        assertEquals(FIRST_BOOK_ID, bookId);
        assertThat(actualBook.getTitle())
                .isNotEqualTo(oldTitle)
                .isEqualTo(newTitle);
    }

    @Test
    @DisplayName("Удаление книги по ID")
    void deleteByIdTest() {
        final Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        final Long countBeforeDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();

        em.detach(book);

        bookRepository.deleteById(FIRST_BOOK_ID);

        final Long countAfterDelete = em.getEntityManager()
                .createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();
        final Book deletedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
        assertThat(countAfterDelete).isEqualTo(countBeforeDelete - 1);

        final BookComment comment = em.find(BookComment.class, 1L);
        assertThat(comment).isNull();
    }

    @Test
    @DisplayName("Найти все книги по жанру")
    void findAllByGenreNameTest() {
        final List<Book> books = bookRepository.findAllByGenreName(SECOND_GENRE_NAME);
        assertThat(books)
                .isNotNull()
                .hasSize(2)
                .allMatch(b -> b.getGenre().getName().equals(SECOND_GENRE_NAME));
    }

    @Test
    @DisplayName("Найти все книги по ID автора")
    void findAllByAuthorIdTest() {
        final List<Book> books = bookRepository.findAllByAuthorId(AUTHOR_ID);
        assertThat(books)
                .isNotNull()
                .hasSize(2)
                .allMatch(b -> b.getAuthor().getId().equals(AUTHOR_ID));
    }
}