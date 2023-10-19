package com.my0803.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect		// AOP 기능을 가진 모듈이라는 뜻
@Component	// 빈으로 등록하겠다
public class SampleAdvice {

	Logger logger =   LoggerFactory.getLogger(SampleAdvice.class);
	// @Before : 위치를 지정하는데 위치를 가기전에 실행해서 동작시키는 기능
	@Before("execution(* com.my0803.myapp.service.BoardService*.*(..))")	// BoardService*.* : BoardService 에 있는 모든 메소드
	public void startLog() {
		
		logger.info("AOP로 로그를 찍어봅니다. 각 메소드를 들어갈때 메세지가 출력됩니다.");
		System.out.println("이것을 sysout으로 찍어보는 기능입니다.");
	}
	
	
}
