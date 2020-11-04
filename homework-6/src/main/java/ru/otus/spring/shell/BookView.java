package ru.otus.spring.shell;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookView {

    private String title;
    private String text;
    private String author;
    private String genre;

    public String toShellView() {
        return "Book{" +
                "title='" + title + '\'' +
                ", \nauthor='" + author + '\'' +
                ", \ngenre='" + genre + '\'' +
                ", \ntext='" + text + '\'' +
                '}';
    }
}
