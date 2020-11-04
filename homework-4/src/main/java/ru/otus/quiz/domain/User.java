package ru.otus.quiz.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public class User {

    @Getter
    public final String name;

    @Getter
    public final Locale locale;
}
