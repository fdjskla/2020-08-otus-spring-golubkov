package ru.otus.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("@annotation(ru.otus.logging.Loggable)")
    public void allLoggable() {}

    @Before("allLoggable()")
    public void log(JoinPoint joinPoint) {
        log.info("Method run : " + joinPoint.getSignature().getName());
    }
}
