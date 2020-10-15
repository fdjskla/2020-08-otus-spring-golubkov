package ru.otus.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.infrastructure.IOService;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.service.LoginService;
import ru.otus.quiz.service.QuizRunner;

@Component
@RequiredArgsConstructor
public class QuizApplication {

    private static final String SUCCESS_RESULT = "quiz.result.success";
    private static final String FAILURE_RESULT = "quiz.result.fail";

    private final LoginService loginService;
    private final QuizRunner quizRunner;
    private final IOService ioService;
    private final QuizProps quizProps;
    private final MessageSource quizLocalization;

    public void takeQuiz() {
        final User user = loginService.login();
        final long numberOfRightAnswers = quizRunner.runQuiz();
        String result = numberOfRightAnswers > quizProps.getPassPoints()
                ? SUCCESS_RESULT
                : FAILURE_RESULT;
        final String resultMessage = quizLocalization.getMessage(
                result,
                new String[]{user.getName(), String.valueOf(numberOfRightAnswers)},
                quizProps.getLocale()
        );
        ioService.printLine(resultMessage);
    }
}
