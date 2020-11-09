package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.jpa.AuthorRepository;
import ru.otus.spring.jpa.BookRepository;
import ru.otus.spring.jpa.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookCrudService implements BookService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void insert(Book book) {
        checkAndUpdateAuthor(book);
        checkAndUpdateGenre(book);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        checkAndUpdateAuthor(book);
        checkAndUpdateGenre(book);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(bookRepository.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllWithComments() {
        return bookRepository.getAllWithComments();
    }

    private void checkAndUpdateGenre(Book book) {
        final Genre genre = book.getGenre();
        Genre genreFromDb = genreRepository.getByName(genre.getName());
        if (genreFromDb != null) {
            book.setGenre(genreFromDb);
        }
        Genre saved = genreRepository.save(genre);
        book.setGenre(saved);
    }

    private void checkAndUpdateAuthor(Book book) {
        final Author author = book.getAuthor();
        final Author authorFromDB = authorRepository.getByName(author.getName());
        if (authorFromDB != null) {
            book.setAuthor(authorFromDB);
        }
        final Author saved = authorRepository.save(author);
        book.setAuthor(saved);
    }

}
