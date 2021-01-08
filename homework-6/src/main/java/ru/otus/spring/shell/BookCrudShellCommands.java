package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCrudShellCommands {

    private final BookService bookService;
    private final CommentService commentService;

    @ShellMethod(value = "Insert book", key = {"ib", "insertBook"})
    public void insert(
            @ShellOption String title,
            @ShellOption String text,
            @ShellOption String author,
            @ShellOption String genre
    ) {
        final Book book = new Book(null, title, text, new Author(null, author), new Genre(null, genre));
        bookService.insert(book);
    }

    @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
    public void update(
            @ShellOption Long id,
            @ShellOption("t") String title,
            @ShellOption("x") String text,
            @ShellOption("a") String author,
            @ShellOption("g") String genre
    ) {
        final Book book = new Book(id, title, text, new Author(null, author), new Genre(null, genre));
        bookService.update(book);
    }

    @ShellMethod(value = "Delete book", key = {"db", "deleteBook"})
    public void delete(
            @ShellOption Long id
    ) {
        bookService.delete(id);
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public String getById(
            @ShellOption Long id
    ) {
        List<CommentView> comments = commentService.getCommentsForBook(id).stream()
                .map(comment -> new CommentView(comment.getUser(), comment.getText()))
                .collect(Collectors.toList());
        return bookService.getById(id)
                .map(book ->
                        new BookWithCommentsView(
                                new BookView(
                                        book.getTitle(),
                                        book.getText(),
                                        book.getAuthor().getName(),
                                        book.getGenre().getName()
                                ),
                                comments
                        ).toShellView()
                )
                .orElse("no book found");
    }

    @ShellMethod(value = "Get all books", key = {"ab", "allBooks"})
    public String getAll() {
        return bookService.getAll().stream()
                .map(b -> new BookView(b.getTitle(), b.getText(), b.getAuthor().getName(), b.getGenre().getName()))
                .map(BookView::toShellView)
                .collect(Collectors.joining("\n"));
    }

}
