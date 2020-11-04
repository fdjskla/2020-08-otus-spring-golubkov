package ru.otus.quiz.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quiz")
@AllArgsConstructor
@NoArgsConstructor
public class QuizProps {

    @Getter
    @Setter
    private String source;

    @Getter
    @Setter
    private Integer passPoints;

    @Getter
    @Setter
    private String success;

    @Getter
    @Setter
    private String fail;
}
