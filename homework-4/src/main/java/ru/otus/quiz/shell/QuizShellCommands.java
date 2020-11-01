package ru.otus.quiz.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.config.ShellProps;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.service.LocalizationService;
import ru.otus.quiz.service.LoginService;
import ru.otus.quiz.service.QuizRunner;

import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {

    private final LocalizationService localizationService;
    private final ShellProps shellProps;
    private final QuizProps quizProps;
    private final QuizRunner quizRunner;
    private final LoginService loginService;

    private User user;

    @ShellMethod(value = "Login for quiz", key = {"l", "login"})
    public String login(
            @ShellOption(defaultValue = "student") String name,
            @ShellOption(defaultValue = "en") String locale
    ) {
        user = loginService.login(name, new Locale(locale));
        return localizationService.getLocalized(shellProps.getHello(), user.locale, user.name);
    }

    @ShellMethod(value = "Take quiz", key = {"q", "quiz"})
    @ShellMethodAvailability(value = "isLoggedInWithValidLocale")
    public String takeQuiz() {
        long numberOfRightAnswers = quizRunner.runQuiz(user);

        String result = numberOfRightAnswers > quizProps.getPassPoints()
                ? quizProps.getSuccess()
                : quizProps.getFail();

        return localizationService.getLocalized(
                result,
                user.getLocale(),
                user.getName(),
                String.valueOf(numberOfRightAnswers)
        );
    }

    private Availability isLoggedInWithValidLocale() {
        final String errorMessage = localizationService.getLocalized(shellProps.getError());
        return user == null
                ? Availability.unavailable(errorMessage)
                : Availability.available();
    }
}
