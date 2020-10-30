package ru.otus.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.infrastructure.IOService;
import ru.otus.quiz.domain.User;
import ru.otus.quiz.service.LoginService;
import ru.otus.quiz.service.QuizRunner;

@Component
public class QuizApplication {

    private static final String QUIZ_RESULT = "Thank you, %s, for taking our quiz. Your result is %d. You %s";
    private static final String SUCCESS_RESULT = "passed!";
    private static final String FAILURE_RESULT = "should try next time.";

    private final LoginService loginService;
    private final QuizRunner quizRunner;
    private final IOService ioService;
    private final Integer pointsToPass;

    public QuizApplication(
            LoginService loginService,
            QuizRunner quizRunner,
            IOService ioService,
            @Value("${quiz.pass.points}") Integer pointsToPass
    ) {
        this.loginService = loginService;
        this.quizRunner = quizRunner;
        this.ioService = ioService;
        this.pointsToPass = pointsToPass;
    }

    public void takeQuiz() {
        final User user = loginService.login();
        final long numberOfRightAnswers = quizRunner.runQuiz();
        String result = numberOfRightAnswers > pointsToPass
                ? SUCCESS_RESULT
                : FAILURE_RESULT;
        ioService.printLine(
                String.format(QUIZ_RESULT, user.getName(), numberOfRightAnswers, result)
        );
    }
}
