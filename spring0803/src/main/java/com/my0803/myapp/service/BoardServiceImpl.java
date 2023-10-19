package com.my0803.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my0803.myapp.domain.BoardVo;
import com.my0803.myapp.domain.SearchCriteria;
import com.my0803.myapp.persistance.BoardService_Mapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	private BoardService_Mapper bsm;	// �������� ����
		
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {	// �Ű������� �ִ� �����ڸ� ��� -> sqlSession �� ���Թްڴ�
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);	// mybatis�� ����Ϸ��� �޼ҵ�� persistance�� ����� -> BoardService_Mapper Interface ����
	
	}
		
	@Override
	public int boardInsert(BoardVo bv) {
		
		int value = bsm.boardInsert(bv);
		int bidx = bv.getBidx();
		// System.out.println("�Է� �Ŀ�  bidx ����?"+bidx);
		bsm.boardOriginBidxUpdate(bidx);
		
		return value;
	}

	@Override
	public ArrayList<BoardVo> boardList(SearchCriteria scri) {
		
		int value = (scri.getPage()-1)*15;
		scri.setPage(value);	// setPage �� ��Ƽ� �� �������� BoardService_Mapper.xml ������ �������� �޾Ƽ� ���Ծ� ����
		
		//mybatis ����
		ArrayList<BoardVo> alist = bsm.boardList(scri);
		return alist;
	}
	
	@Override
	public int boardTotalCount(SearchCriteria scri) {
		
		int value = bsm.boardTotalCount(scri);
		return value;
	}

	@Override
	public BoardVo boardSelectOne(int bidx) {
		
		bsm.boardCntUpdate(bidx);
		
		BoardVo bv = bsm.boardSelectOne(bidx);
		
		return bv;
	}
	
	@Override
	public int boardCntUpdate(int bidx) {
		int value = bsm.boardCntUpdate(bidx);
		return value;
	}

	@Override
	public int boardModify(BoardVo bv) {
		int value = bsm.boardModify(bv);
		return value;
	}
	//public int boardDelete(BoardVo bv) �� ���
//	@Override	
//	public int boardDelete(BoardVo bv) {
//		int value = bsm.boardDelete(bv);
//		return value;
//	}
	
	@Override
	public int boardDelete(int bidx, String pwd) {
		
		String bidxs = bidx + "";
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("bidx", bidxs);
		hm.put("pwd", pwd);
		
		int value = bsm.boardDelete(hm);	// persistance�� Mapper���� HashMap ���� mybatis �޼ҵ� �������ֱ�
		
		return value;
	}
	
	//@Transactional	// �������� �۾������� �� �۾����� �����ִ� annotation -> ����ϸ� ������Ʈ ���� �ȵ�
	@Override
	public int boardReply(BoardVo bv) {
		int depth = bv.getDepth();
		bsm.boardDepthUpdate(depth);
		
		int value = bsm.boardReply(bv);
		
		return value;
	}
}

