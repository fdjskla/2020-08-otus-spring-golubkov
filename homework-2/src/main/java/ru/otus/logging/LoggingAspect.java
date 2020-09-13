package ru.otus.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("@target(ru.otus.logging.Loggable)")
    public void log(JoinPoint joinPoint) {
        log.info("Quiz run : " + joinPoint.getSignature().getName());
    }
}
