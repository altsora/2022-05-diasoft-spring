package ru.diasoft.spring.booklibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.diasoft.spring.booklibrary.util.Constants.AUTHOR_MAPPER;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT * FROM author", AUTHOR_MAPPER);
    }

    @Override
    public Optional<Author> findById(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            final Author author = jdbc.queryForObject("SELECT * FROM author WHERE id = :id", params, AUTHOR_MAPPER);
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}