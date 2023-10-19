package com.my0803.myapp.service;

import java.util.ArrayList;

import com.my0803.myapp.domain.MemberVo;
//interface : 접속할 수 있는 화면이나 기둥
public interface MemberService {
	// 미완성된 메소드로 구성되어 있는 클래스가 아닌 클래스
	// 저장하면 파일 확장자가 .class 이지만 사용 용도가 클래스는 아니다.
	// 스프링에서 사용할 메소드 이름을 정의하는 곳 : 메소드 선언

	public int memberInsert(MemberVo mv);		// MemberVo -> 빨간줄import
	
	public MemberVo memberLogin(String memberId, String memberPwd);
	
	public MemberVo memberLogin(String memberId);	// Login2로 변경안해도 되나...?
	
	public int memberIdCheck(String memberId);
	
	public ArrayList<MemberVo> memberList(); 	//ArrayList -> 빨간줄import
	
}






