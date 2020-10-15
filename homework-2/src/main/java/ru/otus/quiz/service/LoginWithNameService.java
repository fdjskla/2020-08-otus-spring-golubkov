package ru.otus.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.infrastructure.IOService;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.exception.NameIsNeededException;

@Service
@RequiredArgsConstructor
public class LoginWithNameService implements LoginService {

    private final IOService ioService;

    @Override
    public User login() {
        ioService.printLine("Enter your name:");
        final String name = ioService.readLine();
        if (name == null || name.strip().isBlank()) {
            throw new NameIsNeededException();
        }
        return new User(name);
    }
}
