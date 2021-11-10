package com.springboot.cruddemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Pointcut("execution(public * com.springboot.cruddemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(public * com.springboot.cruddemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("execution(public * com.springboot.cruddemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage() ")
	private void forAppFlow() {
	}

	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		String methodSignature = joinPoint.getSignature().toString();

		logger.info("Called from @Before aspect" + methodSignature);

		Object[] methodParameters = joinPoint.getArgs();

		for (Object temp : methodParameters)
			logger.info("Arguments : " + temp);
	}

	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String methodSignature = joinPoint.getSignature().toString();

		logger.info("Called from @AfterReturning aspect" + methodSignature);

		logger.info("result : " + result);
	}
}
