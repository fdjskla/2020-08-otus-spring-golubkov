package ru.otus.quiz.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@AllArgsConstructor
@NoArgsConstructor
public class QuizProps {

    private Integer passPoints;
    private Locale locale;

    public Integer getPassPoints() {
        return passPoints;
    }

    public void setPassPoints(Integer passPoints) {
        this.passPoints = passPoints;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
