package ru.otus.quiz.dao;

import org.junit.jupiter.api.Test;
import ru.otus.quiz.exception.QuizParsingException;

import static org.junit.jupiter.api.Assertions.*;

class QuizDaoTest {

    @Test
    void testEmptyQuizParsing() {
        final var parser = new QuizCsvDao("/empty.csv");
        final var quiz = parser.loadQuiz();
        assertTrue(quiz.isQuizOver());
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        final var parser = new QuizCsvDao("/noAnswer.csv");
        assertThrows(QuizParsingException.class, parser::loadQuiz);
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var parser = new QuizCsvDao("/noOptions.csv");
        final var quiz = parser.loadQuiz();
        assertEquals("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]", quiz.nextQuestion());
        quiz.answer("4");
        assertTrue(quiz.isQuizOver());
        assertEquals(quiz.result(), 1);
    }
}