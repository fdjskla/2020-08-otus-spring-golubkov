package ru.otus.spring.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.CommentService;

import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class CommentCrudShellCommands {

    private final CommentService commentService;

    @ShellMethod(value = "add comment", key = {"ac", "addComment"})
    public void addComment(
            @ShellOption long bookId,
            @ShellOption String userName,
            @ShellOption String text
    ) {
        commentService.addComment(bookId, userName, text);
    }

    @ShellMethod(value = "update comment", key = {"uc", "updateComment"})
    public void updateComment(
            @ShellOption long commentId,
            @ShellOption String text
    ) {
        commentService.updateComment(commentId, text);
    }

    @ShellMethod(value = "delete comment", key = {"dc", "deleteComment"})
    public void deleteComment(
            @ShellOption long commentId
    ) {
        commentService.deleteComment(commentId);
    }

    @ShellMethod(value = "comment by userName", key = {"userc", "userComments"})
    public String getCommentsByUser(
            @ShellOption String userName
    ) {
        return commentService.getCommentsByUser(userName)
                .stream()
                .map(c -> new CommentView(c.getUser(), c.getText()))
                .map(CommentView::toShellView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "comment for book", key = {"bookc", "bookComments"})
    public String getCommentsForBook(
            @ShellOption long bookId
    ) {
        return commentService.getCommentsForBook(bookId)
                .stream()
                .map(c -> new CommentView(c.getUser(), c.getText()))
                .map(CommentView::toShellView)
                .collect(Collectors.joining("\n"));
    }
}
