package ru.otus.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataJpaTest
@Import(BookRepositoryJPA.class)
public class BookRepositoryTest {

    private static final String UPDATED_TEXT = "updated";
    private static final Book BOOK_ONE = new Book(1L, "bookTitle", "text", new Author(1L, "Author1"), new Genre(1L, "romance"));
    private static final Book BOOK_TWO = new Book(2L, "someTitle", "bla-bla-bla", new Author(2L, "NewAuthor"), new Genre(2L, "detective"));

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepositoryJPA bookRepository;

    @Test
    @DisplayName("get all books")
    public void getAllTest() {
        final List<Book> all = bookRepository.getAll();
        assertThat(all)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(BOOK_ONE, BOOK_TWO);
    }

    @Test
    @DisplayName("get book by id")
    public void getById() {
        final Book book = bookRepository.getById(BOOK_TWO.getId());
        assertThat(book)
                .usingRecursiveComparison()
                .isEqualTo(BOOK_TWO);
    }

    @Test
    @DisplayName("delete book")
    public void deleteBook() {
        bookRepository.deleteById(BOOK_ONE.getId());
        final Book deleted = entityManager.getEntityManager().find(Book.class, BOOK_ONE.getId());
        assertThat(deleted).isNull();
    }

    @Test
    @DisplayName("update book")
    public void updateBook() {
        Book updated = new Book(
                BOOK_ONE.getId(),
                BOOK_ONE.getTitle(),
                UPDATED_TEXT,
                BOOK_ONE.getAuthor(),
                BOOK_ONE.getGenre()
        );
        bookRepository.save(updated);

        assertThat(entityManager.getEntityManager().find(Book.class, BOOK_ONE.getId()))
                .extracting(Book::getText)
                .isEqualTo(UPDATED_TEXT);
    }

    @Test
    @DisplayName("insert book")
    public void insertBook() {
        Book newBook = new Book(null, "newTitle", "newText", new Author(2L, "NewAuthor"), new Genre(1L, "romance"));
        final Book savedBook = bookRepository.save(newBook);

        final Book newBookFromDb = entityManager.getEntityManager().find(Book.class, savedBook.getId());
        assertThat(newBookFromDb)
                .usingRecursiveComparison()
                .isEqualTo(newBook);
    }

}
