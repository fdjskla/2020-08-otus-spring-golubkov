package ru.otus.quiz.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.quiz.exception.QuizParsingException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        properties = {
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
class QuizDaoTest {

    @Autowired
    private QuizCsvDao parser;

    @Test
    void testEmptyQuizParsing() {
        final var quiz = parser.loadQuiz(Locale.ENGLISH);
        assertThat(quiz.isQuizOver()).isTrue();
    }

    @Test
    void testQuizWithoutAnswerParsing() {
        assertThrows(QuizParsingException.class, () -> parser.loadQuiz(Locale.FRENCH));
    }

    @Test
    void testQuizWithoutNoOptions() {
        final var quiz = parser.loadQuiz(Locale.CHINESE);
        assertThat("What is the result of equation \"2*2\"?" + System.lineSeparator() + "[]").isEqualTo(quiz.nextQuestion());
        quiz.answer("4");
        assertThat(quiz.isQuizOver()).isTrue();
        assertThat(quiz.result()).isEqualTo(1);
    }
}