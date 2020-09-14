package ru.otus.quiz.domain;

import ru.otus.quiz.exception.QuizIsOverException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleQuiz implements Quiz {

    private final List<QuizQuestion> questions;
    private int questionNumber;
    private Boolean[] results;

    public SimpleQuiz(List<QuizQuestion> questions) {
        this.questions = questions;
        this.questionNumber = 0;
        this.results = new Boolean[questions.size()];
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
        return Arrays.stream(results)
                .filter(obj -> Objects.nonNull(obj) && obj)
                .count();
    }

    @Override
    public long numberOfQuestions() {
        return questions.size();
    }
}
