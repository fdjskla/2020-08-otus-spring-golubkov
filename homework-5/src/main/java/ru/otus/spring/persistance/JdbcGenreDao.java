package ru.otus.spring.persistance;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class JdbcGenreDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        jdbc.update("insert into genres(`name`) values(:name)", params, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void update(Genre genre) {
        final Map<String, Object> params = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
        jdbc.update("update genres set `name` = :name where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("delete from genres where id = :id", params);
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("select id, name from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public Genre getByName(String name) {
        final Map<String, Object> params = Map.of("name", name);
        return jdbc.queryForObject("select id, name from genres where name = :name", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genres", new GenreMapper());
    }
}
