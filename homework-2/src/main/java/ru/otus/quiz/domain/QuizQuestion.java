package ru.otus.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class QuizQuestion {

    @Getter
    private final String question;
    @Getter
    private final String answer;
    @Getter
    private final List<String> options;
}
