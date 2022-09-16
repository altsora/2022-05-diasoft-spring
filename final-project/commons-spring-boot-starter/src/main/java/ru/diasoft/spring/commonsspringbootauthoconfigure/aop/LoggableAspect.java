package ru.diasoft.spring.commonsspringbootauthoconfigure.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j2
@Aspect
@Component
public class LoggableAspect {
    @PostConstruct
    private void init() {
        log.info("Initializing LoggableAspect 'loggableAspect'");
    }

    @Pointcut("within(@ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable *)")
    public void loggableClass() {
    }

    @Pointcut("@annotation(ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable)")
    public void loggableMethod() {
    }

    @Before("loggableClass() || loggableMethod()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            final String className = joinPoint.getSignature().getDeclaringTypeName();
            final String methodName = joinPoint.getSignature().getName();
            log.debug("Method {}.{}() has been called with input parameters: {}",
                    className, methodName, joinPoint.getArgs());
        }
    }

    @AfterReturning(
            pointcut = "loggableClass() || loggableMethod()",
            returning = "result"
    )
    public void logAfterMethodCall(JoinPoint joinPoint, Object result) {
        if (log.isDebugEnabled()) {
            final String className = joinPoint.getSignature().getDeclaringTypeName();
            final String methodName = joinPoint.getSignature().getName();
            if (result != null) {
                log.debug("Method {}.{}() return value: {}", className, methodName, result);
            } else {
                log.debug("Method {}.{}() return", className, methodName);
            }
        }
    }

    @AfterThrowing(
            pointcut = "loggableClass() || loggableMethod()",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("An exception has been thrown in {}().\nCause: {}",
                joinPoint.getSignature().getName(), exception.getMessage());
    }
}
