package ru.otus.quiz.domain;

import ru.otus.quiz.exception.QuizIsOverException;

import java.util.List;
import java.util.stream.IntStream;

public class SimpleQuiz implements Quiz {

    private final List<QuizQuestion> questions;
    private int questionNumber;
    private boolean[] results;

    public SimpleQuiz(List<QuizQuestion> questions) {
        this.questions = questions;
        this.questionNumber = 0;
        this.results = new boolean[questions.size()];
    }

    @Override
    public boolean isQuizOver() {
        return questionNumber >= questions.size();
    }

    @Override
    public String nextQuestion() {
        if (questionNumber >= questions.size()) {
            throw new QuizIsOverException();
        }
        final QuizQuestion quizQuestion = questions.get(questionNumber);
        questionNumber++;
        return quizQuestion.getQuestion() + System.lineSeparator() + quizQuestion.getOptions();
    }

    @Override
    public void answer(String answer) {
        results[questionNumber - 1] = answer.strip().equalsIgnoreCase(questions.get(questionNumber - 1).getAnswer());
    }

    @Override
    public long result() {
        return IntStream.range(0, results.length)
                .filter(i -> results[i])
                .count();
    }
}
