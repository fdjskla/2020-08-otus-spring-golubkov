package ru.otus.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@annotation(ru.otus.logging.Loggable)")
    public void allLoggable() {
    }

    @Before("allLoggable()")
    public void log(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        log.info("Method run : " + joinPoint.getSignature().getName());
    }
}
