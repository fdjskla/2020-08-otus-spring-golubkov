package ru.otus.quiz.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.exception.QuizParsingException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDaoTest {

    private final Locale en = Locale.ENGLISH;

    @Autowired
    private MessageSource quizLocalization;

    @Test
    void testEmptyQuizParsing() {
        final var parser = new QuizCsvDao(new QuizProps("/empty.csv", 1, en), quizLocalization);
        final var quiz = parser.loadQuiz(en);
        assertTrue(quiz.quizIsOver());
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        final var parser = new QuizCsvDao(new QuizProps("/noAnswer.csv", 1, en), quizLocalization);
        assertThrows(QuizParsingException.class, () -> parser.loadQuiz(en));
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var parser = new QuizCsvDao(new QuizProps("/noOptions.csv", 1, en), quizLocalization);
        final var quiz = parser.loadQuiz(en);
        assertEquals("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]", quiz.nextQuestion());
        assertTrue(quiz.answerIsCorrect("4"));
        assertTrue(quiz.quizIsOver());
    }
}