package ru.otus.quiz.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QuizConfig {

    private final List<QuizQuestion> questions;

    public Quiz getQuiz() {
        return new SimpleQuiz(questions);
    }
}
