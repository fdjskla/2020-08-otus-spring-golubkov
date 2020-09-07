package ru.otus.quiz.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.quiz.exception.QuizIsOverException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQuizTest {

    private Quiz quiz;

    @BeforeEach
    public void initQuiz() {
        quiz = new SimpleQuiz(List.of(
                new QuizQuestion("?", "!", List.of())
        ));
    }

    @Test
    void testQuizIsOver() {
        assertFalse(quiz.quizIsOver());
        quiz.nextQuestion();
        assertTrue(quiz.quizIsOver());
    }

    @Test
    void testNextQuestion() {
        final var nextQuestion = quiz.nextQuestion();
        assertEquals("?" + System.lineSeparator() + "[]", nextQuestion);
    }

    @Test
    void testExceptionOnNextQuestionIfQuizIsOver() {
        quiz.nextQuestion();
        assertThrows(QuizIsOverException.class, quiz::nextQuestion);
    }

    @Test
    void testAnswerIsCorrect() {
        quiz.nextQuestion();
        assertFalse(quiz.answerIsCorrect("wrong"));
        assertTrue(quiz.answerIsCorrect("!"));
    }

    @Test
    void quizResults() {
        assertEquals("Thank you for taking our quiz", quiz.quizResults());
    }
}