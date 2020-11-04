package ru.otus.spring.persistance;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        final Author author = new Author(
                resultSet.getLong("author_id"),
                resultSet.getString("author")
        );
        final Genre genre = new Genre(
                resultSet.getLong("genre_id"),
                resultSet.getString("genre")
        );
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("text"),
                author,
                genre
        );
    }
}
