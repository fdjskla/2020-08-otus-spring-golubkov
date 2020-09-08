package ru.otus.quiz.dao;

import org.junit.jupiter.api.Test;
import ru.otus.quiz.exception.QuizParsingException;

import static org.junit.jupiter.api.Assertions.*;

class QuizDaoTest {

    @Test
    void testEmptyQuizParsing() {
        final var parser = new QuizCsvDao("/empty.csv", 1);
        final var quiz = parser.loadQuiz();
        assertTrue(quiz.quizIsOver());
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        final var parser = new QuizCsvDao("/noAnswer.csv", 1);
        assertThrows(QuizParsingException.class, parser::loadQuiz);
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var parser = new QuizCsvDao("/noOptions.csv", 1);
        final var quiz = parser.loadQuiz();
        assertEquals("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]", quiz.nextQuestion());
        assertTrue(quiz.answerIsCorrect("4"));
        assertTrue(quiz.quizIsOver());
    }
}