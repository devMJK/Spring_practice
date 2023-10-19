package com.my0803.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 로그인 체크 인증 인터셉터 클래스
// AuthInterceptor을 bean에 등록해야함 -> 서블릿에 들어있으므로 servlet-context.xml 에 등록
public class AuthInterceptor extends HandlerInterceptorAdapter {	// HandlerInterceptorAdapter 을 상속받아서 AuthInterceptor 를 구현함
	
	@Override 	// 재정의
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler
			) throws Exception {
				
		// 세션값이 있는지 없는지 체크
		HttpSession session = request.getSession();
		
		boolean tf = false;
		if (session.getAttribute("midx") == null) {	// session 의 midx 값이 null 인지 아닌지
			System.out.println(request);
			// 세션에 이동하려는 주소를 담는다
			saveUrl(request);
			
			String location =request.getContextPath()+"/member/memberLogin.do";
			response.sendRedirect(location);		// midx 값이 null 이면 sendRedirect 를 사용하여 로그인페이지로 돌아가게 설정 
			return false;
		}else {
			return true;
		}
		//return true;
	}

	// 이동하려는 주소를 세션에 담는 메소드
	public void saveUrl(HttpServletRequest req) {	// 주소가 넘어오면 Session에 담아
	
		String uri = req.getRequestURI();		// 전체경로 값을 추출하여 넘김
		String query =req.getQueryString();		// 파라미터 값을 추출하여 넘김
		
		if (query == null || query.equals("null")) {
			query="";
		}else {
			query = "?" +query;
		}
		System.out.println(uri+query);
		if(req.getMethod().equals("GET")) {	// 대소문자 중요!!!
			req.getSession().setAttribute("saveUrl", uri+query);
		}
	}

	
}



