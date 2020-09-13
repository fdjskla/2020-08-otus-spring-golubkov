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
        quiz = new SimpleQuiz(
                List.of(
                        new QuizQuestion("1", "1", List.of()),
                        new QuizQuestion("2", "2", List.of("0,1,2")),
                        new QuizQuestion("3", "3", List.of("0,1,2,3"))
                ),
                2
        );
    }

    @Test
    void testQuizIsOver() {
        assertFalse(quiz.quizIsOver());
        quiz.nextQuestion();
        quiz.nextQuestion();
        quiz.nextQuestion();
        assertTrue(quiz.quizIsOver());
    }

    @Test
    void testNextQuestion() {
        final var nextQuestion = quiz.nextQuestion();
        assertEquals("1" + System.lineSeparator() + "[]", nextQuestion);
    }

    @Test
    void testExceptionOnNextQuestionIfQuizIsOver() {
        quiz.nextQuestion();
        quiz.nextQuestion();
        quiz.nextQuestion();
        assertThrows(QuizIsOverException.class, quiz::nextQuestion);
    }

    @Test
    void testAnswerIsCorrect() {
        quiz.nextQuestion();
        assertFalse(quiz.answerIsCorrect("wrong"));
        assertTrue(quiz.answerIsCorrect("1"));
    }

    @Test
    void quizResults() {
        assertEquals(
                "Thank you for taking our quiz. Your result is 0/3. You should try next time.",
                quiz.quizResults()
        );
    }
}