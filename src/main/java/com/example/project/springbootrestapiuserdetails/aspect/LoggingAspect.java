package com.example.project.springbootrestapiuserdetails.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * <h>logBeforeAndAfterServiceMethods</h>
     * Adds trace logging before a proceeding join point method call.
     * @param pjp The proceeding joint point
     * @return Result of method call
     * @throws Throwable
     */
    //@Around("execution(* com.example.project..*.* (..))")
    /*@Pointcut("execution(* com.example.project..*.*(..))" +
            " || execution(* com.example.project.service..*(..))" +
            " || execution(* com.example.project.controller..*(..))" +
            " || execution(* com.example.project.repository..*(..))")*/
    @Around("execution(* com.example.project.springbootrestapiuserdetails..*(..))")
    public Object logBeforeAndAfterServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("{} has started execution.", pjp.getSignature());
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object resultOfMethodCall = pjp.proceed();
        stopWatch.stop();
        Object[] arguments = pjp.getArgs();
        LOGGER.info("{} finished execution", pjp.getSignature());
        //Log method execution time
        LOGGER.info("Execution time of " + pjp.getSignature().getName() + "." + pjp.getSignature().getDeclaringType().getName() + " with parameters" + Arrays.toString(arguments) + ":: " + stopWatch.getTotalTimeMillis() + " ms");
        return resultOfMethodCall;
    }

    @AfterThrowing(pointcut = "execution(* com.example.project.springbootrestapiuserdetails..*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getMessage() : "NULL");
    }

    /*@Before("execution(* com.example.project.springbootrestapiuserdetails.*.*(..))")
    public void logArguments(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("-----" + className + "." + methodName + "() -----");

        for (int i = 0; i < arguments.length...
    }*/
}