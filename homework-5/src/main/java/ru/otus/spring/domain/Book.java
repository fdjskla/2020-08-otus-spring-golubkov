package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Book {

    private Long id;
    private String title;
    private String text;
    private Author author;
    private Genre genre;
}
