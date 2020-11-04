package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.quiz.config.QuizProps;
import ru.otus.quiz.config.ShellProps;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableConfigurationProperties({QuizProps.class, ShellProps.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
