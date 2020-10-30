package ru.otus.quiz.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.exception.QuizParsingException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDaoTest {

    @Autowired
    private MessageSource quizLocalization;

    @Test
    void testEmptyQuizParsing() {
        final var parser = new QuizCsvDao(new QuizProps(1, Locale.ENGLISH), quizLocalization);
        final var quiz = parser.loadQuiz();
        assertTrue(quiz.isQuizOver());
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        final var parser = new QuizCsvDao(new QuizProps(1, Locale.FRENCH), quizLocalization);
        assertThrows(QuizParsingException.class, parser::loadQuiz);
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var parser = new QuizCsvDao(new QuizProps(1, Locale.CHINESE), quizLocalization);
        final var quiz = parser.loadQuiz();
        assertEquals("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]", quiz.nextQuestion());
        quiz.answer("4");
        assertThat(quiz.isQuizOver()).isTrue();
        assertThat(quiz.result()).isEqualTo(1);
    }
}