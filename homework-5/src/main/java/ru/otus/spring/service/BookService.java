package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void insert(Book book);

    void update(Book book);

    void delete(Long id);

    Optional<Book> getById(Long id);

    List<Book> getAll();
}
