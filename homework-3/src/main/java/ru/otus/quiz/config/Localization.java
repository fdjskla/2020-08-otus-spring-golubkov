package ru.otus.quiz.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class Localization {

    @Bean(name = "quizLocalization")
    public MessageSource quizLocalization() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/resource");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
