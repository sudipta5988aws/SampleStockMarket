package com.jpmc.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Aspect to log time taken for a method to execute.
 * Useful to track a thread excecution time
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect 
{
  @Around("@annotation(com.jpmc.app.annotation.TimeLoggable)")
  public Object logTimeTakenOfMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
  {
    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();
    log.info("Execution time of :: " + stopWatch.getTotalTimeMillis() + " ms");
    return result;
  }
}
