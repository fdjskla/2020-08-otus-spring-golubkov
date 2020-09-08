package ru.otus.quiz.domain;

import ru.otus.quiz.exception.QuizIsOverException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleQuiz implements Quiz {

    private static final String QUIZ_RESULTS = "Thank you for taking our quiz. Your result is %d/%d. You %s";

    private final List<QuizQuestion> questions;
    private final int pointsToPass;
    private int questionNumber;
    private Boolean[] results;

    public SimpleQuiz(List<QuizQuestion> questions, int pointsToPass) {
        this.questions = questions;
        this.pointsToPass = pointsToPass;
        this.questionNumber = 0;
        this.results = new Boolean[questions.size()];
    }

    @Override
    public boolean quizIsOver() {
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
    public boolean answerIsCorrect(String answer) {
        final boolean correct = answer.strip().equalsIgnoreCase(questions.get(questionNumber - 1).getAnswer());
        results[questionNumber - 1] = correct;
        return correct;
    }

    @Override
    public String quizResults() {
        final long numberOfRightAnswers = Arrays.stream(results)
                .filter(obj -> Objects.nonNull(obj) && obj)
                .count();
        String result = numberOfRightAnswers > pointsToPass
                ? "passed!"
                : "should try next time.";
        return String.format(QUIZ_RESULTS, numberOfRightAnswers, results.length, result);
    }
}
