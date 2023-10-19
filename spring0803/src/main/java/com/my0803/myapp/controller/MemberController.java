package com.my0803.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.my0803.myapp.domain.MemberVo;
import com.my0803.myapp.service.MemberService;

@Controller		// Controller 용도의 객체 생성 요청 -> 빨간줄은 import
@RequestMapping(value="/member")
public class MemberController {

	@Autowired	// 객체주입요청
	MemberService ms;	//MemberService는 부모고 MemberServiceImpl은 자식이고 자식객체를 부모타입에 집어넣는 업캐스팅
	
	@Autowired	// Bean에 등록된 암호화 모듈 객체를 주입해달라고 요청
	private BCryptPasswordEncoder bCryptPasswordEncoder;	// BCryptPasswordEncoder 빨간줄은 import
	
	@RequestMapping(value="/memberJoin.do")		// RequestMapping 빨간줄은 import
	public String memberJoin() {
		
		return "/member/memberJoin";		// views 폴더 밖에 있는 중간값 -> servlet-context.xml 가서 라인 20에서 views 지우기
	}
	
	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(MemberVo mv) {	// MemberVo 빨간줄은 import
		
		//String memberid = request.getParameter("memberid");	// 기존 jsp방식 -> Spring 에서는 라인19처럼 MemberVo 가 한번에 받아와서 input 객체들의 값을  바인딩한다.
		//System.out.println("아이디는?"+mv.getMemberId());		// Member Vo 가 값을 받아오는지 확인
		
		String birth = mv.getMemberYear()+mv.getMemberMonth()+mv.getMemberDay();	// 바인딩해서 가져온 값을 birth에 담고
		mv.setMemberBirth(birth);	// MemberVo의 MemberBirth에 넣기
		
		String memberPwd2 = bCryptPasswordEncoder.encode(mv.getMemberPwd());	// 넘어온 값을 암호화시켜서 String 타입의 memberPwd2로 저장
		mv.setMemberPwd(memberPwd2);
		
		int value = ms.memberInsert(mv);
		
		//포워드방식이 아닌 sendRedirect방식 -> 재지시 방식
		return "redirect:/index.jsp";		// memberJoinAction을 처리하고 나면 index.jsp 페이지로 넘어감
											// index.jsp 는 기본페이지이므로 생략해서 "redirect:/" 로만 작성 가능
	}
	
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		
		return "/member/memberLogin";
	}
	//로그인 버튼을 누르면 처리하고 메인으로 이동하는 메소드 만들기
	
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,	// RequestParam 빨간줄은 import
			//HttpSession session		// session 에 있는 연결성을 꺼내서 Spring에 주입을 해줌
			RedirectAttributes rttr,
			HttpServletRequest request
			) {	
				
		// 기존 방식
		// MemberVo mv = ms.memberLogin(memberId, memberPwd);	// 내부에서 mybatis가 HashMap을 처리해서 mv로 값을 넘김
		
		// 암호 비교 방식 : memberId 를 넘겨서 해당되는 비밀번호를 가져와서 암호화되지 않은 비밀번호와 비교
		MemberVo mv = ms.memberLogin(memberId);
		
		String path = "";
		if(mv != null) {
			if (bCryptPasswordEncoder.matches(memberPwd, mv.getMemberPwd())) {
			// session 을 빼고 1회용 모델 클래스 RedirectAttribute 로 변경
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMemberName());
			
			if (request.getSession().getAttribute("saveUrl") != null) {
				path = 	(String)request.getSession().getAttribute("saveUrl").toString().substring(request.getContextPath().length()+1);		// (String) 을 사용하여 강제 형변환, toString() 으로 글자를 자름
				}else {
				path = "index.jsp";
				}	
			}
		}else {
			path = "member/memberLogin.do";	// 로그인이 안됐으면 다시 로그인 페이지로이동
		}
		return "redirect:/" + path;
	}

	@RequestMapping(value="/memberLogout.do")
	public String memberLoginout(HttpSession session) {
		// session 초기화
		session.removeAttribute("midx");
		session.removeAttribute("memberName");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@ResponseBody	// import, 응답할때 Body로 보낸다 -> String이 아닌 객체로
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(String memberId) {
		
		String str = null;
		int value = ms.memberIdCheck(memberId);	//매개변수로 넘어온 아이디를 받아서 아이디체크 메소드를 실행시킴 -> 받은 아이디가 있으면 1 아니면 0이 value 에 담긴다
		
		str = "{\"value\" : \""+value+"\"}";
		
		return str;	// JSON 문서형태로 리턴값을 보내줘야함
	}
	
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
				
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
		
		return "member/memberList";	
	}
}
