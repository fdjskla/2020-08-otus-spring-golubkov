package ru.otus.quiz;

import ru.otus.quiz.domain.Quiz;
import ru.otus.quiz.domain.QuizConfig;

import java.util.Scanner;

class SimpleQuizRunner implements QuizRunner {

    public void runQuiz(QuizConfig quizConfig) {
        final Quiz quiz = quizConfig.getQuiz();
        final Scanner scanner = new Scanner(System.in);
        while (!quiz.quizIsOver()) {
            System.out.println(quiz.nextQuestion());
            quiz.answerIsCorrect(scanner.next());
        }
        System.out.println(quiz.quizResults());
    }
}
