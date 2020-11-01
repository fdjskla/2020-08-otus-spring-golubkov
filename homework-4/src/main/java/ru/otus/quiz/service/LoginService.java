package ru.otus.quiz.service;

import ru.otus.quiz.domain.User;

import java.util.Locale;

public interface LoginService {

    User login(String name, Locale locale);
}
