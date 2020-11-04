package ru.otus.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.persistance.AuthorDao;
import ru.otus.spring.persistance.BookDao;
import ru.otus.spring.persistance.GenreDao;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("book service should ")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookServiceTest {

    private static final String UPDATED_TEXT = "updated";
    private static final Book BOOK_ONE = new Book(3L, "bookTitle1", "text0", new Author(3L, "Author1"), new Genre(3L, "romance"));
    private static final Book BOOK_TWO = new Book(4L, "someTitle2", "bla-bla", new Author(4L, "NewAuthor"), new Genre(4L, "detective"));

    @MockBean
    private BookDao bookDao;
    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private GenreDao genreDao;
    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("get all books")
    public void getAllTest() {
        when(bookDao.getAll()).thenReturn(List.of(BOOK_ONE, BOOK_TWO));
        final List<Book> all = bookService.getAll();
        assertThat(all)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(BOOK_ONE, BOOK_TWO);
    }

    @Test
    @DisplayName("get no book for unknown id")
    public void notFoundById() {
        when(bookDao.getById(175L)).thenReturn(null);
        final Optional<Book> book = bookService.getById(175L);
        assertThat(book).isEmpty();
    }

    @Test
    @DisplayName("get book by id")
    public void getById() {
        when(bookDao.getById(BOOK_ONE.getId())).thenReturn(BOOK_ONE);
        final Optional<Book> book = bookService.getById(BOOK_ONE.getId());
        assertThat(book.get())
                .usingRecursiveComparison()
                .isEqualTo(BOOK_ONE);
    }

    @Test
    @DisplayName("delete book")
    public void deleteBook() {
        bookService.delete(BOOK_ONE.getId());
        verify(bookDao, times(1)).deleteById(BOOK_ONE.getId());
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
        bookService.update(updated);
        verify(bookDao, timeout(1)).update(updated);
    }

    @Test
    @DisplayName("insert book")
    public void insertBook() {
        Book newBook = new Book(null, "newTitle", "newText", new Author(null, "myAuthor"), new Genre(null, "myGenre"));
        bookService.insert(newBook);
        verify(bookDao, times(1)).insert(newBook);
    }

}
