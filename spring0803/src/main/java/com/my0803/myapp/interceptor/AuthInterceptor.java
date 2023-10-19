package com.my0803.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// �α��� üũ ���� ���ͼ��� Ŭ����
// AuthInterceptor�� bean�� ����ؾ��� -> ������ ��������Ƿ� servlet-context.xml �� ���
public class AuthInterceptor extends HandlerInterceptorAdapter {	// HandlerInterceptorAdapter �� ��ӹ޾Ƽ� AuthInterceptor �� ������
	
	@Override 	// ������
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler
			) throws Exception {
				
		// ���ǰ��� �ִ��� ������ üũ
		HttpSession session = request.getSession();
		
		boolean tf = false;
		if (session.getAttribute("midx") == null) {	// session �� midx ���� null ���� �ƴ���
			System.out.println(request);
			// ���ǿ� �̵��Ϸ��� �ּҸ� ��´�
			saveUrl(request);
			
			String location =request.getContextPath()+"/member/memberLogin.do";
			response.sendRedirect(location);		// midx ���� null �̸� sendRedirect �� ����Ͽ� �α����������� ���ư��� ���� 
			return false;
		}else {
			return true;
		}
		//return true;
	}

	// �̵��Ϸ��� �ּҸ� ���ǿ� ��� �޼ҵ�
	public void saveUrl(HttpServletRequest req) {	// �ּҰ� �Ѿ���� Session�� ���
	
		String uri = req.getRequestURI();		// ��ü��� ���� �����Ͽ� �ѱ�
		String query =req.getQueryString();		// �Ķ���� ���� �����Ͽ� �ѱ�
		
		if (query == null || query.equals("null")) {
			query="";
		}else {
			query = "?" +query;
		}
		System.out.println(uri+query);
		if(req.getMethod().equals("GET")) {	// ��ҹ��� �߿�!!!
			req.getSession().setAttribute("saveUrl", uri+query);
		}
	}

	
}



