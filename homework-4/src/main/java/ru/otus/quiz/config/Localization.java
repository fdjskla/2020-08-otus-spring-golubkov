package ru.otus.quiz.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class Localization {

    @Bean
    public MessageSource quizLocalization() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/quiz");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public MessageSource shellLocalization() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/shell");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
