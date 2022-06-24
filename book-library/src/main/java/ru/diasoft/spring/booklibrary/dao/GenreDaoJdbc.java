package ru.diasoft.spring.booklibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.diasoft.spring.booklibrary.util.Constants.GENRE_MAPPER;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT * FROM genre", GENRE_MAPPER);
    }

    @Override
    public Optional<Genre> findById(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            final Genre genre = jdbc.queryForObject("SELECT * FROM genre WHERE id = :id", params, GENRE_MAPPER);
            return Optional.ofNullable(genre);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
