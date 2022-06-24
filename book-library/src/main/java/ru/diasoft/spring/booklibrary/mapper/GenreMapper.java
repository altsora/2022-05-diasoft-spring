package ru.diasoft.spring.booklibrary.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.diasoft.spring.booklibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
