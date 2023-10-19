package com.my0803.myapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BoardTimeCheckAdvice {

	Logger logger =   LoggerFactory.getLogger(BoardTimeCheckAdvice.class);
	
	@Around("execution(* com.my0803.myapp.service.BoardService*.*(..))")
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {	// JoinPoint를 진행시키기 위한 메소드
		Object result = null;
		
		logger.info("AOP 시작전입니다.");
		long startTime = System.currentTimeMillis();
		
		result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		logger.info("AOP 시작후입니다.");
		
		long loadingTime = endTime - startTime;
		
		logger.info(pjp.getSignature().getName()+":"+loadingTime);
		
		return result;
	}
		
}



