package ru.otus.quiz.shell;

import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.quiz.QuizRunner;
import ru.otus.quiz.config.ShellProps;

import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {

    private final MessageSource shellLocalization;
    private final ShellProps shellProps;
    private final QuizRunner quizRunner;

    private String name;
    private Locale locale;

    @ShellMethod(value = "Login for quiz", key = {"l", "login"})
    public String login(
            @ShellOption(defaultValue = "student") String name,
            @ShellOption(defaultValue = "en") String locale
    ) {
        this.name = name;
        this.locale = new Locale(locale);
        return shellLocalization.getMessage(shellProps.getHello(), new String[]{this.name}, this.locale);
    }

    @ShellMethod(value = "Start quiz", key = {"q", "quiz"})
    @ShellMethodAvailability(value = "isLoggedInWithValidLocale")
    public void startQuiz() {
        quizRunner.runQuiz(locale);
    }

    private Availability isLoggedInWithValidLocale() {
        return name == null || locale == null
                ? Availability.unavailable("Log in and set locale")
                : Availability.available();
    }
}
