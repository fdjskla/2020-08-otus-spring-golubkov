package ru.otus.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.infrastructure.IOService;
import ru.otus.logging.Loggable;
import ru.otus.quiz.dao.QuizDao;
import ru.otus.quiz.domain.Quiz;

@Service
class SimpleQuizRunner implements QuizRunner {

    private static final String QUIZ_RESULTS = "Thank you for taking our quiz. Your result is %d/%d. You %s";

    private final QuizDao quizDao;
    private final IOService ioService;
    private final Integer pointsToPass;

    public SimpleQuizRunner(QuizDao quizDao, IOService ioService, @Value("${quiz.pass.points}") Integer pointsToPass) {
        this.quizDao = quizDao;
        this.ioService = ioService;
        this.pointsToPass = pointsToPass;
    }

    @Loggable
    public void runQuiz() {
        final Quiz quiz = quizDao.loadQuiz();
        while (!quiz.isQuizOver()) {
            ioService.printLine(quiz.nextQuestion());
            quiz.answer(ioService.readLine());
        }

        final long numberOfRightAnswers = quiz.result();
        String result = numberOfRightAnswers > pointsToPass
                ? "passed!"
                : "should try next time.";
        ioService.printLine(String.format(QUIZ_RESULTS, numberOfRightAnswers, quiz.numberOfQuestions(), result));
    }
}
