package il.fridman.tempus.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@within(Loggable)")
    public void loggingMethods() {
    }

    @Around("loggingMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Starting execution of method: {} with arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        Object result = joinPoint.proceed();
        log.info("Method {} executed successfully with result: {}",
                joinPoint.getSignature().getName(),
                result);
        return result;
    }
}
