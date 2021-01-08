package ru.otus.spring.jpa;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAll();

    Book getById(long id);

    Book save(Book book);

    void deleteById(long id);
}
