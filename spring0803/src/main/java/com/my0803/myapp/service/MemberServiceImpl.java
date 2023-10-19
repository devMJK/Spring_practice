package com.my0803.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my0803.myapp.domain.MemberVo;
import com.my0803.myapp.persistance.MemberService_Mapper;

@Service
public class MemberServiceImpl implements MemberService {
	private MemberService_Mapper msm;		
	
	@Autowired					// mybatis 객체를 주입받음(타입으로 메모리에서 객체를 찾는다.)
	//SqlSession sqlSession;		// Session 타입으로 sqlSession을 지정 -> root-context.xml 에는 SqlSessionTemplate 타입에 sqlSession 을 넣었는데 부모는 mybatis가 아닌 ibatis에 들어있기 때문 
	public MemberServiceImpl(SqlSession sqlSession) {	// 생성자의 매개변수에 주입
		this.msm = sqlSession.getMapper(MemberService_Mapper.class);
	}
	
	@Override
	public int memberInsert(MemberVo mv) {
	
		// getMapper 메소드 : mybatis에서 사용하는 메소드를 정의해놓은 인터페이스를 부른다.
		// MemberService_Mapper msm = sqlSession.getMapper(MemberService_Mapper.class);		// MemberService_Mapper 인터페이스에서 불러온다.
		int value = msm.memberInsert(mv);
					
		return value;
	}

	@Override
	public MemberVo memberLogin(String memberId, String memberPwd) {
		MemberVo mv = null;
		
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put("memberId", memberId);
		hm.put("memberPwd", memberPwd);
		
		// mybatis와 연동할 준비 -> 여기서 mybatis 는 sqlSession
		// mybatis에서 사용할 메소드를 정의해놓은 인터페이스
		// MemberService_Mapper msm = sqlSession.getMapper(MemberService_Mapper.class);
		mv = msm.memberLogin(hm);
				
		return mv;
	}

	@Override	// 오버라이딩(재정의), 오버로딩(같은 이름 다른 역할 메소드) 둘다
	public MemberVo memberLogin(String memberId) {
		MemberVo mv = null;
		mv = msm.memberLogin2(memberId);
		
		// System.out.println("저장된 비밀번호"+mv.getMemberPwd());
		
		return mv;
	}

	@Override
	public int memberIdCheck(String memberId) {
		int value=0;
		value = msm.memberIdCheck(memberId);	// 해당되는 memberId를 받아서 mybatis 용으로 돌린다
		return value;
	}

	@Override
	public ArrayList<MemberVo> memberList() {
		// System.out.println("msm 값?"+msm);
		// mybatis 연동
		ArrayList<MemberVo> alist = msm.memberList();
		return alist;
	}

}
