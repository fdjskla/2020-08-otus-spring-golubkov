package ru.otus.quiz.parser;

import org.junit.jupiter.api.Test;
import ru.otus.quiz.domain.Quiz;
import ru.otus.quiz.domain.QuizConfig;
import ru.otus.quiz.exception.QuizParsingException;

import static org.junit.jupiter.api.Assertions.*;

class QuizParserTest {

    @Test
    void testEmptyQuizParsing() {
        final var parser = new QuizCsvParser("/empty.csv");
        final var quizConfig = parser.parse();
        assertTrue(quizConfig.getQuiz().quizIsOver());
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        final var parser = new QuizCsvParser("/noAnswer.csv");
        assertThrows(QuizParsingException.class, parser::parse);
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var parser = new QuizCsvParser("/noOptions.csv");
        final var quizConfig = parser.parse();
        final var quiz = quizConfig.getQuiz();
        assertEquals("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]", quiz.nextQuestion());
        assertTrue(quiz.answerIsCorrect("4"));
        assertTrue(quiz.quizIsOver());
    }
}