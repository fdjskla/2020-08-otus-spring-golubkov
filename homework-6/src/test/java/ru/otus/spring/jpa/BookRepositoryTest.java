package ru.otus.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataJpaTest
@Import(BookRepositoryJPA.class)
public class BookRepositoryTest {

    private static final String UPDATED_TEXT = "updated";
    private static final Book BOOK_ONE = new Book(1L, "bookTitle", "text", new Author(1L, "Author1"), new Genre(1L, "romance"), List.of());
    private static Book bookTwo = new Book(2L, "someTitle", "bla-bla-bla", new Author(2L, "NewAuthor"), new Genre(2L, "detective"), List.of());
    private static final Comment COMMENT_ONE = new Comment(1L, bookTwo, "Petya", "text");
    private static final Comment COMMENT_TWO = new Comment(2L, bookTwo, "Vasya", "bla-bla-bla");

    static {
        bookTwo.setComments(List.of(COMMENT_ONE, COMMENT_TWO));
    }
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
                .containsExactlyInAnyOrder(BOOK_ONE, bookTwo);
    }

    @Test
    @DisplayName("get book by id")
    public void getById() {
        final Book book = bookRepository.getById(bookTwo.getId());
        assertThat(book)
                .usingRecursiveComparison()
                .isEqualTo(bookTwo);
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
                BOOK_ONE.getGenre(),
                List.of()
        );
        bookRepository.save(updated);

        assertThat(entityManager.getEntityManager().find(Book.class, BOOK_ONE.getId()))
                .extracting(Book::getText)
                .isEqualTo(UPDATED_TEXT);
    }

    @Test
    @DisplayName("insert book")
    public void insertBook() {
        Book newBook = new Book(null, "newTitle", "newText", new Author(2L, "NewAuthor"), new Genre(1L, "romance"), List.of());
        final Book savedBook = bookRepository.save(newBook);

        final Book newBookFromDb = entityManager.getEntityManager().find(Book.class, savedBook.getId());
        assertThat(newBookFromDb)
                .usingRecursiveComparison()
                .isEqualTo(newBook);
    }

}
