package ru.otus.quiz.domain;

import ru.otus.quiz.exception.QuizIsOverException;

import java.util.List;

public class SimpleQuiz implements Quiz {

    private final List<QuizQuestion> questions;
    private int questionNumber;

    public SimpleQuiz(List<QuizQuestion> questions) {
        this.questions = questions;
        this.questionNumber = 0;
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
        return answer.strip().equalsIgnoreCase(questions.get(questionNumber - 1).getAnswer());
    }

    @Override
    public String quizResults() {
        return "Thank you for taking our quiz";
    }
}
