package ru.otus.spring.persistance;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class JdbcBookDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Book book) {
        final Map<String, Object> params = Map.of(
                "title", book.getTitle(),
                "text", book.getText(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()
        );
        jdbc.update(
                "insert into books(`title`, `text`, `author_id`, `genre_id`) " +
                        "values(:title, :text, :author_id, :genre_id)",
                params
        );
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "text", book.getText(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()
        );
        jdbc.update(
                "update books " +
                        "set `title`= :title, `text` = :text, `author_id` = :author_id, `genre_id` = :genre_id " +
                        "where `id` = :id",
                params
        );
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("delete from books where `id` = :id", params);
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        try {
            return jdbc.queryForObject(
                    "select b.id, b.text, b.title, b.author_id, a.name as author, b.genre_id, g.name as genre" +
                            " from books b left join authors a on b.author_id = a.id" +
                            " left join genres g on b.genre_id = g.id" +
                            " where b.id = :id",
                    params,
                    new BookMapper()
            );
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.id, b.text, b.title, b.author_id, a.name as author, b.genre_id, g.name as genre " +
                " from books b left join authors a on b.author_id = a.id " +
                        "left join genres g on b.genre_id = g.id",
                new BookMapper()
        );
    }

}
