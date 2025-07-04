package com.foresys.core.aop.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foresys.core.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@EnableAspectJAutoProxy
@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class LogAspect {

	@Around("execution(* com.foresys..*Controller.*(..)) && args(params,..)")
	public Object logBefore(ProceedingJoinPoint joinpoint, Object params) throws Throwable {
		try {
			log.info("==== LogAspect.logBefore ReqParam : {}", ConvertUtil.ObjToJsonstring(params));
		}catch (Exception e) {
			log.info("==== LogAspect.logBefore ReqParam : Log cannot be printed.");
		}

		Object proceed = joinpoint.proceed();

		try {
			Object res;
			if (proceed instanceof Map) res = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(proceed);
			else res = proceed;
			log.info("==== LogAspect.logBefore ResParam : {}", ConvertUtil.ObjToJsonstring(res));
		} catch (Exception e) {
			log.info("==== LogAspect.logBefore ReqParam : Log cannot be printed.");
		}

		return proceed;
	}

}