package ru.otus.quiz.dao;

import ru.otus.quiz.domain.Quiz;

import java.util.Locale;

public interface QuizDao {

    Quiz loadQuiz(Locale locale);
}
