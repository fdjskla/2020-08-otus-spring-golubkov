package ru.otus.spring.persistance;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class JdbcAuthorDao implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author getByName(String name) {
        final Map<String, Object> params = Map.of("name", name);
        try {
            return jdbc.queryForObject("select id, name from authors where name = :name", params, new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        jdbc.update("insert into authors(`name`) values(:name)", params, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void update(Author author) {
        final Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName()
        );
        jdbc.update("update authors set `name` = :name where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("delete from authors where id = :id", params);
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("select id, name from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from authors", new AuthorMapper());
    }
}
