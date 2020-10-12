package ru.otus.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.infrastructure.IOService;
import ru.otus.logging.Loggable;
import ru.otus.quiz.dao.QuizDao;
import ru.otus.quiz.domain.Quiz;

@Service
@RequiredArgsConstructor
class SimpleQuizRunner implements QuizRunner {

    private final QuizDao quizDao;
    private final IOService ioService;

    @Loggable
    public long runQuiz() {
        final Quiz quiz = quizDao.loadQuiz();
        while (!quiz.isQuizOver()) {
            ioService.printLine(quiz.nextQuestion());
            quiz.answer(ioService.readLine());
        }

        return quiz.result();
    }
}
