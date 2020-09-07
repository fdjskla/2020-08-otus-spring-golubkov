package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.quiz.QuizRunner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.runQuiz();
    }
}
