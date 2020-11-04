package ru.otus.spring.jpa;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> getAll();

    Genre getById(long id);

    Genre getByName(String name);

    Genre save(Genre book);

    void deleteById(long id);
}
