package ru.otus.quiz.domain;

public interface Quiz {

    boolean isQuizOver();

    String nextQuestion();

    void answer(String answer);

    long result();
}
