package ru.diasoft.spring.booklibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.booklibrary.domain.BookComment;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static ru.diasoft.spring.booklibrary.util.Constants.FETCH_GRAPH;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryImpl implements BookCommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookComment> findById(Long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> findAll() {
        final EntityGraph<?> graph = em.getEntityGraph("graph.bookcomment-book");
        return em.createQuery("SELECT c FROM BookComment c join fetch c.book", BookComment.class)
                .setHint(FETCH_GRAPH, graph)
                .getResultList();
    }

    @Override
    @Transactional
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null) {
            em.persist(bookComment);
            return bookComment;
        }
        return em.merge(bookComment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM BookComment c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteOne(BookComment bookComment) {
        em.remove(bookComment);
    }
}
