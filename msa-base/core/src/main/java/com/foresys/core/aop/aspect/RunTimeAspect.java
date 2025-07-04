package com.foresys.core.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class RunTimeAspect {

	@Around("@annotation(com.foresys.core.annotation.RunTime)")
	public Object runTimeLog(ProceedingJoinPoint joinpoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinpoint.proceed();
        
        stopWatch.stop();
        
        log.info("############# Excute Service time warning : {}", joinpoint.getSignature().toShortString());
        log.info(stopWatch.prettyPrint());
        log.info("{} sec", stopWatch.getTotalTimeSeconds());

        return proceed;
	}

}
