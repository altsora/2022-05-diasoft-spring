package ru.diasoft.spring.booklibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        final TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
