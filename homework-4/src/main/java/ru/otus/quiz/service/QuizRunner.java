package ru.otus.quiz.service;

import ru.otus.quiz.domain.User;

public interface QuizRunner {

    /**
     * @return number of right answers
     */
    long runQuiz(User user);
}
