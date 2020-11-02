package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.persistance.AuthorDao;
import ru.otus.spring.persistance.BookDao;
import ru.otus.spring.persistance.GenreDao;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookCrudService implements BookService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

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
        final Genre genre = book.getGenre();
        Genre genreFromDb = genreDao.getByName(genre.getName());
        final Long newGenreId;
        if (genreFromDb == null) {
            newGenreId = genreDao.insert(genre);
        } else {
            newGenreId = genreFromDb.getId();
        }
        genre.setId(newGenreId);
    }

    private void checkAndUpdateAuthor(Book book) {
        final Author author = book.getAuthor();
        final Author authorFromDB = authorDao.getByName(author.getName());
        final Long authorId;
        if (authorFromDB == null) {
            authorId = authorDao.insert(author);
        } else {
            authorId = authorFromDB.getId();
        }
        author.setId(authorId);
    }

}
