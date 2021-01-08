package ru.otus.spring.jpa;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAll();

    Author getById(long id);

    Author getByName(String name);

    Author save(Author author);

    void deleteById(long id);
}
