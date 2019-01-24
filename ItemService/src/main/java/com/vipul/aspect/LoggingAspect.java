package com.vipul.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	@Pointcut("execution(* com.vipul.*..*(..))")
	private void allVipulPublicMethods() {
	}

	@Around("allVipulPublicMethods()")
	public Object logAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
		logger.info("Entering {}()", pjp.getSignature().getName());
		Object returnValue = pjp.proceed();
		logger.info("Exited {}()", pjp.getSignature().getName());
		return returnValue;
	}

}
