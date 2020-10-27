package ru.otus.spring.persistance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookDao should ")
@SpringBootTest(
        properties = {
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
@Transactional
public class BookDaoTest {

    private static final String UPDATED_TEXT = "updated";
    private static final Book BOOK_ONE = new Book(1L, "bookTitle", "text", new Author(1L, "Author1"), new Genre(1L, "romance"));
    private static final Book BOOK_TWO = new Book(2L, "someTitle", "bla-bla-bla", new Author(2L, "NewAuthor"), new Genre(2L, "detective"));

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("get all books")
    public void getAllTest() {
        final List<Book> all = bookDao.getAll();
        assertThat(all)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(BOOK_ONE, BOOK_TWO);
    }

    @Test
    @DisplayName("get book by id")
    public void getById() {
        final Book book = bookDao.getById(BOOK_TWO.getId());
        assertThat(book)
                .usingRecursiveComparison()
                .isEqualTo(BOOK_TWO);
    }

    @Test
    @DisplayName("delete book")
    public void deleteBook() {
        final List<Book> all = bookDao.getAll();
        bookDao.deleteById(BOOK_ONE.getId());
        assertThat(bookDao.getAll())
                .hasSize(all.size() - 1)
                .noneMatch(book -> book.getId().equals(BOOK_ONE.getId()));
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
        bookDao.update(updated);

        assertThat(bookDao.getById(BOOK_ONE.getId()))
                .extracting(Book::getText)
                .isEqualTo(UPDATED_TEXT);
    }

    @Test
    @DisplayName("insert book")
    public void insertBook() {
        Book newBook = new Book(null, "newTitle", "newText", new Author(2L, "NewAuthor"), new Genre(1L, "romance"));
        final Long bookKey = bookDao.insert(newBook);
        newBook.setId(bookKey);
        final List<Book> all = bookDao.getAll();
        assertThat(all)
                .hasSize(3)
                .filteredOn(book -> book.getId() > 2)
                .hasSize(1)
                .element(0)
                .usingRecursiveComparison()
                .isEqualTo(newBook);
    }

}
