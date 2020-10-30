package ru.otus.quiz.exception;

public class QuizParsingException extends RuntimeException {

    public QuizParsingException(String message) {
        super(message);
    }

    public QuizParsingException(Throwable cause) {
        super(cause);
    }
}
