package ru.otus.spring.persistance;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
