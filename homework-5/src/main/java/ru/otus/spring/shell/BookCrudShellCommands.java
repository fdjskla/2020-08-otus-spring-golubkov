package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCrudShellCommands {

    private final BookService bookService;

    @ShellMethod(value = "Insert book", key = {"i", "insert"})
    public void insert(
            @ShellOption String title,
            @ShellOption String text,
            @ShellOption String author,
            @ShellOption String genre
    ) {
        final Book book = new Book(null, title, text, new Author(null, author), new Genre(null, genre));
        bookService.insert(book);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
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

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public void delete(
            @ShellOption Long id
    ) {
        bookService.delete(id);
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    public String getbyId(
            @ShellOption Long id
    ) {
        return bookService.getById(id)
                .map(book ->
                        new BookView(
                                book.getTitle(),
                                book.getText(),
                                book.getAuthor().getName(),
                                book.getGenre().getName()
                        ).toShellView()
                )
                .orElse("no book found");
    }

    @ShellMethod(value = "Get all books", key = {"a", "all"})
    public String getAll() {
        return bookService.getAll().stream()
                .map(b -> new BookView(b.getTitle(), b.getText(), b.getAuthor().getName(), b.getGenre().getName()))
                .map(BookView::toShellView)
                .collect(Collectors.joining("\n"));
    }

}
