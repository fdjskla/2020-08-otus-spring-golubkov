package ru.otus.quiz.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@AllArgsConstructor
@NoArgsConstructor
public class QuizProps {

    @Getter
    @Setter
    private String dataSource;
    @Getter
    @Setter
    private Integer passPoints;
    @Getter
    @Setter
    private Locale locale;
}
