package ru.otus.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class InfrastructureConfiguration {

    @Bean
    public IOService ioService() {
        return new ConsoleIOService(System.out, new Scanner(System.in));
    }
}
