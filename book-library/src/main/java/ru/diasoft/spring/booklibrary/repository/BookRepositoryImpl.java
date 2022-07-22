package ru.diasoft.spring.booklibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.booklibrary.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static ru.diasoft.spring.booklibrary.util.Constants.FETCH_GRAPH;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        //Способы решения N+1:
        // 1. Граф (указывается при запросе и над сущностью)
        // 2. join fetch прямо в запросе
        // 3. @Fetch(SUBSELECT) + @BatchSize(N) над полем сущности - для N родителей будет запрос дочки
        final EntityGraph<?> graph = em.getEntityGraph("book-author");
        final TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre", Book.class);
        query.setHint(FETCH_GRAPH, graph);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        try {
            final Book book = em.createQuery("SELECT b FROM Book b WHERE b.id = :id", Book.class)
                    .setParameter("id", id)
                    .setHint(FETCH_GRAPH, em.getEntityGraph("book-author-genre"))
                    .getSingleResult();
            return Optional.ofNullable(book);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Long saveOrUpdate(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book.getId();
        }
        return em.merge(book).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        final Query query = em.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        em.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllByGenreName(String genreName) {
        return em.createQuery("SELECT b FROM Book b WHERE b.genre.name = :name", Book.class)
                .setParameter("name", genreName)
                .getResultList();
    }

    @Override
    public List<Book> findAllByAuthorId(Long authorId) {
        return em.createQuery("SELECT b FROM Book b WHERE b.author.id = :id", Book.class)
                .setParameter("id", authorId)
                .getResultList();
    }

}
