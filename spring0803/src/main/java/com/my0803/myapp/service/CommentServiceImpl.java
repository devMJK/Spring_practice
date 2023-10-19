package com.my0803.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my0803.myapp.domain.CommentVo;
import com.my0803.myapp.persistance.CommentService_Mapper;

@Service
public class CommentServiceImpl implements CommentService {	// CommentService Interface 를 내려받아서 구현함

	private CommentService_Mapper csm;	// 전역변수 선언

	@Autowired	// mybatis 와 연동작업
	public CommentServiceImpl(SqlSession sqlSession) {	// 매개변수가 있는 생성자를 사용 -> sqlSession 에 주입받겠다
		this.csm = sqlSession.getMapper(CommentService_Mapper.class);	// mybatis가 사용하려는 메소드는 persistance에 만들어 -> CommentService_Mapper Interface 생성
	
	}
	
	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx) {

		ArrayList<CommentVo> alist = csm.commentSelectAll(bidx);
		
		return alist;
	
	}

	@Override
	public int commentInsert(CommentVo cv) {
		
		int value = csm.commentInsert(cv);
		
		return value;
	}

	@Override
	public int commentDelete(int cidx) {
			
		int value = csm.commentDelete(cidx);
		
		return value;
	}

	

}
