package ru.otus.quiz.dao;

import lombok.AllArgsConstructor;
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
import java.util.stream.Collectors;

@AllArgsConstructor
class QuizCsvDao implements QuizDao {

    private static final String DELIMETER = ",";

    private final String resource;

    @Override
    public Quiz loadQuiz() {
        try (
                InputStream quizStream = QuizCsvDao.class.getResourceAsStream(resource);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(quizStream));
        ) {
            List<QuizQuestion> quizQuestions = bufferedReader
                    .lines()
                    .skip(1)
                    .map(line -> {
                        String[] params = line.split(DELIMETER);
                        if (params.length < 2) {
                            throw new QuizParsingException("Question and answer are mandatory");
                        }
                        return new QuizQuestion(
                                params[0],
                                params[1],
                                Arrays.asList(params).subList(2, params.length)
                        );
                    })
                    .collect(Collectors.toList());
            return new SimpleQuiz(quizQuestions);
        } catch (IOException e) {
            throw new QuizParsingException(e);
        }
    }
}
