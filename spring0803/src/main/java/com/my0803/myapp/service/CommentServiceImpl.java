package com.my0803.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my0803.myapp.domain.CommentVo;
import com.my0803.myapp.persistance.CommentService_Mapper;

@Service
public class CommentServiceImpl implements CommentService {	// CommentService Interface �� �����޾Ƽ� ������

	private CommentService_Mapper csm;	// �������� ����

	@Autowired	// mybatis �� �����۾�
	public CommentServiceImpl(SqlSession sqlSession) {	// �Ű������� �ִ� �����ڸ� ��� -> sqlSession �� ���Թްڴ�
		this.csm = sqlSession.getMapper(CommentService_Mapper.class);	// mybatis�� ����Ϸ��� �޼ҵ�� persistance�� ����� -> CommentService_Mapper Interface ����
	
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
