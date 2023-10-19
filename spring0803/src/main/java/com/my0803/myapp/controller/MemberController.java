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

@Controller		// Controller �뵵�� ��ü ���� ��û -> �������� import
@RequestMapping(value="/member")
public class MemberController {

	@Autowired	// ��ü���Կ�û
	MemberService ms;	//MemberService�� �θ�� MemberServiceImpl�� �ڽ��̰� �ڽİ�ü�� �θ�Ÿ�Կ� ����ִ� ��ĳ����
	
	@Autowired	// Bean�� ��ϵ� ��ȣȭ ��� ��ü�� �����ش޶�� ��û
	private BCryptPasswordEncoder bCryptPasswordEncoder;	// BCryptPasswordEncoder �������� import
	
	@RequestMapping(value="/memberJoin.do")		// RequestMapping �������� import
	public String memberJoin() {
		
		return "/member/memberJoin";		// views ���� �ۿ� �ִ� �߰��� -> servlet-context.xml ���� ���� 20���� views �����
	}
	
	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(MemberVo mv) {	// MemberVo �������� import
		
		//String memberid = request.getParameter("memberid");	// ���� jsp��� -> Spring ������ ����19ó�� MemberVo �� �ѹ��� �޾ƿͼ� input ��ü���� ����  ���ε��Ѵ�.
		//System.out.println("���̵��?"+mv.getMemberId());		// Member Vo �� ���� �޾ƿ����� Ȯ��
		
		String birth = mv.getMemberYear()+mv.getMemberMonth()+mv.getMemberDay();	// ���ε��ؼ� ������ ���� birth�� ���
		mv.setMemberBirth(birth);	// MemberVo�� MemberBirth�� �ֱ�
		
		String memberPwd2 = bCryptPasswordEncoder.encode(mv.getMemberPwd());	// �Ѿ�� ���� ��ȣȭ���Ѽ� String Ÿ���� memberPwd2�� ����
		mv.setMemberPwd(memberPwd2);
		
		int value = ms.memberInsert(mv);
		
		//���������� �ƴ� sendRedirect��� -> ������ ���
		return "redirect:/index.jsp";		// memberJoinAction�� ó���ϰ� ���� index.jsp �������� �Ѿ
											// index.jsp �� �⺻�������̹Ƿ� �����ؼ� "redirect:/" �θ� �ۼ� ����
	}
	
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		
		return "/member/memberLogin";
	}
	//�α��� ��ư�� ������ ó���ϰ� �������� �̵��ϴ� �޼ҵ� �����
	
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,	// RequestParam �������� import
			//HttpSession session		// session �� �ִ� ���Ἲ�� ������ Spring�� ������ ����
			RedirectAttributes rttr,
			HttpServletRequest request
			) {	
				
		// ���� ���
		// MemberVo mv = ms.memberLogin(memberId, memberPwd);	// ���ο��� mybatis�� HashMap�� ó���ؼ� mv�� ���� �ѱ�
		
		// ��ȣ �� ��� : memberId �� �Ѱܼ� �ش�Ǵ� ��й�ȣ�� �����ͼ� ��ȣȭ���� ���� ��й�ȣ�� ��
		MemberVo mv = ms.memberLogin(memberId);
		
		String path = "";
		if(mv != null) {
			if (bCryptPasswordEncoder.matches(memberPwd, mv.getMemberPwd())) {
			// session �� ���� 1ȸ�� �� Ŭ���� RedirectAttribute �� ����
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMemberName());
			
			if (request.getSession().getAttribute("saveUrl") != null) {
				path = 	(String)request.getSession().getAttribute("saveUrl").toString().substring(request.getContextPath().length()+1);		// (String) �� ����Ͽ� ���� ����ȯ, toString() ���� ���ڸ� �ڸ�
				}else {
				path = "index.jsp";
				}	
			}
		}else {
			path = "member/memberLogin.do";	// �α����� �ȵ����� �ٽ� �α��� ���������̵�
		}
		return "redirect:/" + path;
	}

	@RequestMapping(value="/memberLogout.do")
	public String memberLoginout(HttpSession session) {
		// session �ʱ�ȭ
		session.removeAttribute("midx");
		session.removeAttribute("memberName");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@ResponseBody	// import, �����Ҷ� Body�� ������ -> String�� �ƴ� ��ü��
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(String memberId) {
		
		String str = null;
		int value = ms.memberIdCheck(memberId);	//�Ű������� �Ѿ�� ���̵� �޾Ƽ� ���̵�üũ �޼ҵ带 �����Ŵ -> ���� ���̵� ������ 1 �ƴϸ� 0�� value �� ����
		
		str = "{\"value\" : \""+value+"\"}";
		
		return str;	// JSON �������·� ���ϰ��� ���������
	}
	
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
				
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
		
		return "member/memberList";	
	}
}
