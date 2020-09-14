package ru.otus.quiz.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shell")
@AllArgsConstructor
@NoArgsConstructor
public class ShellProps {

    @Getter
    @Setter
    private String hello;
    @Getter
    @Setter
    private String error;
}
