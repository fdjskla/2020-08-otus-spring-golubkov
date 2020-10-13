package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.quiz.QuizApplication;
import ru.otus.quiz.config.QuizProps;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableConfigurationProperties(QuizProps.class)
public class Main {

    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class, args);

        var app = context.getBean(QuizApplication.class);
        app.takeQuiz();
    }
}
