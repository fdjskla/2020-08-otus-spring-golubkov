package ru.otus.spring.persistance;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    Long insert(Genre genre);

    void update(Genre genre);

    void deleteById(long id);

    Genre getById(long id);

    List<Genre> getAll();
}
