package ru.otus.spring.persistance;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface AuthorDao {

    Author getByName(String name);

    Long insert(Author author);

    void update(Author author);

    void deleteById(long id);

    Author getById(long id);

    List<Author> getAll();
}
