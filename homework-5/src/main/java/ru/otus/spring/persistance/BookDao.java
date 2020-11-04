package ru.otus.spring.persistance;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    Long insert(Book book);

    void update(Book book);

    void deleteById(long id);

    Book getById(long id);

    List<Book> getAll();
}
