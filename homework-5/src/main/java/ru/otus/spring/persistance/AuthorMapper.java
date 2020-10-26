package ru.otus.spring.persistance;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Author(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
