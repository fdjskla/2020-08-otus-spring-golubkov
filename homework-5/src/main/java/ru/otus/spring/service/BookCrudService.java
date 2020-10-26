package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.persistance.AuthorDao;
import ru.otus.spring.persistance.BookDao;
import ru.otus.spring.persistance.GenreDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookCrudService implements BookService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final Map<String, Genre> genres;

    public BookCrudService(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.genres = genreDao.getAll()
                .stream()
                .collect(Collectors.toMap(
                        Genre::getName,
                        Function.identity()
                ));
    }

    @Override
    @Transactional
    public void insert(Book book) {
        checkAndUpdateAuthor(book);
        checkAndUpdateGenre(book);

        bookDao.insert(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        checkAndUpdateAuthor(book);
        checkAndUpdateGenre(book);

        bookDao.update(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(bookDao.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    private void checkAndUpdateGenre(Book book) {
        final String genreName = book.getGenre().getName();
        Genre genre = genres.get(genreName);
        if (genre == null) {
            final Long newGenreId = genreDao.insert(book.getGenre());
            genre = new Genre(newGenreId, genreName);
            genres.put(genreName, genre);
        }
        book.setGenre(genre);
    }

    private void checkAndUpdateAuthor(Book book) {
        final Author author = book.getAuthor();
        final Author authorFromDB = authorDao.getByName(author.getName());
        Long authorId = null;
        if (authorFromDB == null) {
            authorId = authorDao.insert(author);
        } else {
            authorId = authorFromDB.getId();
        }
        author.setId(authorId);
    }

}
