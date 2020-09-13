package ru.otus.quiz;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.infrastructure.IOService;
import ru.otus.logging.Loggable;
import ru.otus.quiz.dao.QuizDao;
import ru.otus.quiz.domain.Quiz;

@Loggable
@Service
@AllArgsConstructor
class SimpleQuizRunner implements QuizRunner {

    private final QuizDao quizDao;
    private final IOService ioService;

    public void runQuiz() {
        final Quiz quiz = quizDao.loadQuiz();
        while (!quiz.quizIsOver()) {
            ioService.printLine(quiz.nextQuestion());
            quiz.answerIsCorrect(ioService.readLine());
        }

        ioService.printLine(quiz.quizResults());
    }
}
