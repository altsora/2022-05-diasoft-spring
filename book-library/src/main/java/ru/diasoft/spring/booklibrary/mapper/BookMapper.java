package ru.diasoft.spring.booklibrary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.diasoft.spring.booklibrary.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .authorId(rs.getLong("author_id"))
                .genreId(rs.getLong("genre_id"))
                .build();
    }
}