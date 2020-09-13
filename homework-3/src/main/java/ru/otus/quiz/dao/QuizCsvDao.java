package ru.otus.quiz.dao;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.domain.Quiz;
import ru.otus.quiz.domain.QuizQuestion;
import ru.otus.quiz.domain.SimpleQuiz;
import ru.otus.quiz.exception.QuizParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class QuizCsvDao implements QuizDao {

    private static final String DELIMETER = ",";

    private final QuizProps quizProps;
    private final MessageSource quizLocalization;

    @Override
    public Quiz loadQuiz() {
        try (
                InputStream quizStream = QuizCsvDao.class.getResourceAsStream(quizProps.getDataSource());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(quizStream));
        ) {
            List<QuizQuestion> quizQuestions = bufferedReader
                    .lines()
                    .skip(1)
                    .map(this::toQuizQuestion)
                    .collect(Collectors.toList());
            return new SimpleQuiz(quizQuestions, quizProps.getPassPoints());
        } catch (IOException e) {
            throw new QuizParsingException(e);
        }
    }

    private QuizQuestion toQuizQuestion(String line) {
        String[] params = line.split(DELIMETER);
        if (params.length < 2) {
            throw new QuizParsingException("Question and answer are mandatory");
        }
        final Locale locale = quizProps.getLocale();
        final String localizedQuestion = quizLocalization.getMessage(params[0], new String[]{}, locale);
        final String localizedAnswer = quizLocalization.getMessage(params[1], new String[]{}, locale);
        final List<String> localizedOptions = Arrays.asList(params).subList(2, params.length).stream()
                .map(option -> quizLocalization.getMessage(option, new String[]{}, locale))
                .collect(Collectors.toList());
        return new QuizQuestion(localizedQuestion, localizedAnswer, localizedOptions);
    }
}
