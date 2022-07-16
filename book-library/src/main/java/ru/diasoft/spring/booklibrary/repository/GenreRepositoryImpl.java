package ru.diasoft.spring.booklibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        final TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }
}
