package ru.otus.quiz.parser;

import lombok.AllArgsConstructor;
import ru.otus.Main;
import ru.otus.quiz.domain.QuizConfig;
import ru.otus.quiz.domain.QuizQuestion;
import ru.otus.quiz.exception.QuizParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class QuizCsvParser implements QuizParser {

    private static final String DELIMETER = ",";

    private final String resource;

    @Override
    public QuizConfig parse() {
        try (
                InputStream quizStream = QuizCsvParser.class.getResourceAsStream(resource);
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
            return new QuizConfig(quizQuestions);
        } catch (IOException e) {
            throw new QuizParsingException(e);
        }
    }
}
