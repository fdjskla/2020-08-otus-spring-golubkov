package ru.otus.spring.shell;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookWithCommentsView {

    private final BookView book;
    private final List<CommentView> comments;

    public String toShellView() {
        return "BookWithCommentsView{" +
                "book=" + book.toShellView() +
                ", comments=" + comments.stream()
                .map(CommentView::toShellView)
                .collect(Collectors.joining(", \n")) +
                '}';
    }
}
