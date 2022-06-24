package ru.diasoft.spring.booklibrary.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.diasoft.spring.booklibrary.util.Constants.BOOK_MAPPER;

@Log4j2
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM book", BOOK_MAPPER);
    }

    @Override
    public Optional<Book> findById(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            final Book book = jdbc.queryForObject("SELECT * FROM book WHERE id = :id", params, BOOK_MAPPER);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAllByGenre(String genreName) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", genreName);
        return jdbc.query("SELECT b.* " +
                        "FROM book b " +
                        "INNER JOIN genre g ON b.genre_id = g.id " +
                        "WHERE g.name = :name",
                params, BOOK_MAPPER);
    }

    @Override
    public List<Book> findAllByAuthor(Long authorId) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("author_id", authorId);
        return jdbc.query("SELECT * FROM book WHERE author_id = :author_id", params, BOOK_MAPPER);
    }

    @Override
    public Long save(Book book) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthorId());
        params.addValue("genre_id", book.getGenreId());
        final KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO book(title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)", params, kh);
        final long newId = kh.getKey().longValue();
        log.info("Добавлена новая книга с ID {}", newId);
        return newId;
    }

    @Override
    public void delete(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        final int rows = jdbc.update("DELETE FROM book WHERE id = :id", params);
        log.info("Удаление книги с ID {}. Удалено строк: {}", id, rows);
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("author_id", book.getAuthorId());
        params.put("genre_id", book.getGenreId());
        final int rows = jdbc.update("UPDATE book " +
                        "SET title = :title, " +
                        "author_id = :author_id," +
                        "genre_id = :author_id " +
                        "WHERE id = :id",
                params);
        log.info("Обновление книги с ID {}. Обновлено: {} шт.", book.getId(), rows);
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM book", Integer.class);
    }
}