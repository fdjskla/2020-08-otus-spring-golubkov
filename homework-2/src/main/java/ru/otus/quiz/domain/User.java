package ru.otus.quiz.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {

    @Getter
    public final String name;
}
