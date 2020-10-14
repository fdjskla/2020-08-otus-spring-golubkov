package ru.otus.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.infrastructure.IOService;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.exception.NameIsNeededException;

@Service
@RequiredArgsConstructor
public class LoginWithNameService implements LoginService {

    private static final String ENTER_NAME = "quiz.enter";

    private final IOService ioService;
    private final MessageSource quizLocalization;
    private final QuizProps quizProps;

    @Override
    public User login() {
        ioService.printLine(quizLocalization.getMessage(ENTER_NAME, null, quizProps.getLocale()));
        final String name = ioService.readLine();
        if (name == null || name.strip().isBlank()) {
            throw new NameIsNeededException();
        }
        return new User(name);
    }
}
