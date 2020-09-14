package ru.otus.quiz.domain;

public interface Quiz {

    boolean quizIsOver();

    String nextQuestion();

    boolean answerIsCorrect(String answer);

    String quizResults();
}
