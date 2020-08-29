package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.quiz.QuizRunner;
import ru.otus.quiz.domain.QuizConfig;
import ru.otus.quiz.parser.QuizParser;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");

        QuizParser quizParser = context.getBean(QuizParser.class);
        final QuizConfig quizConfig = quizParser.parse();

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.runQuiz(quizConfig);
    }
}
