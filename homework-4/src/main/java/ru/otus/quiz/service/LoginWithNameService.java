package ru.otus.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.exception.LocaleIsNeededException;
import ru.otus.quiz.exception.NameIsNeededException;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LoginWithNameService implements LoginService {

    @Override
    public User login(String name, Locale locale) {
        if (name == null || name.strip().isBlank()) {
            throw new NameIsNeededException();
        }
        if (locale == null) {
            throw new LocaleIsNeededException();
        }
        return new User(name, locale);
    }
}
