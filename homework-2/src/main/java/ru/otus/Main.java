package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import ru.otus.quiz.QuizApplication;

@EnableAspectJAutoProxy
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        var app = context.getBean(QuizApplication.class);
        app.takeQuiz();
    }
}
