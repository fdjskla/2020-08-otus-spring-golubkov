package ru.otus.quiz.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.quiz.exception.QuizIsOverException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleQuizTest {

    private Quiz quiz;

    @BeforeEach
    public void initQuiz() {
        quiz = new SimpleQuiz(
                List.of(
                        new QuizQuestion("1", "1", List.of()),
                        new QuizQuestion("2", "2", List.of("0,1,2")),
                        new QuizQuestion("3", "3", List.of("0,1,2,3"))
                )
        );
    }

    @Test
    void testQuizIsOver() {
        assertThat(quiz.isQuizOver()).isFalse();
        quiz.nextQuestion();
        quiz.nextQuestion();
        quiz.nextQuestion();
        assertThat(quiz.isQuizOver()).isTrue();
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
        quiz.answer("wrong");
        assertThat(quiz.result()).isEqualTo(0l);
        quiz.answer("1");
        assertThat(quiz.result()).isEqualTo(1l);
    }

    @Test
    void quizResults() {
        assertThat(quiz.result()).isEqualTo(0l);
    }
}