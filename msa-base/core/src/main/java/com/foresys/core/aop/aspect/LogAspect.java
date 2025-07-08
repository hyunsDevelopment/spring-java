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
		printLog(params, true);

		Object proceed = joinpoint.proceed();

		printLog(proceed, false);

		return proceed;
	}

	private static void printLog(Object params, boolean isReq) {
		try {
			Object res;
			if (params instanceof Map) res = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(params);
			else res = params;
			log.info("==== LogAspect.logBefore {}Param : {}", isReq ? "Req" : "Res", ConvertUtil.ObjToJsonstring(res));
		}catch (Exception e) {
			log.info("==== LogAspect.logBefore {}Param : Log cannot be printed.", isReq ? "Req" : "Res");
		}
	}

}