package ru.otus.spring.shell;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookView {

    private final String title;
    private final String text;
    private final String author;
    private final String genre;

    public String toShellView() {
        return "Book{" +
                "title='" + title + '\'' +
                ", \nauthor='" + author + '\'' +
                ", \ngenre='" + genre + '\'' +
                ", \ntext='" + text + '\'' +
                '}';
    }
}
