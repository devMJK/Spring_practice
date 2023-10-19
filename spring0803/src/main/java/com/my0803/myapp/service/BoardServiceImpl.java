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
	
	private BoardService_Mapper bsm;	// 전역변수 선언
		
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {	// 매개변수가 있는 생성자를 사용 -> sqlSession 에 주입받겠다
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);	// mybatis가 사용하려는 메소드는 persistance에 만들어 -> BoardService_Mapper Interface 생성
	
	}
		
	@Override
	public int boardInsert(BoardVo bv) {
		
		int value = bsm.boardInsert(bv);
		int bidx = bv.getBidx();
		// System.out.println("입력 후에  bidx 값은?"+bidx);
		bsm.boardOriginBidxUpdate(bidx);
		
		return value;
	}

	@Override
	public ArrayList<BoardVo> boardList(SearchCriteria scri) {
		
		int value = (scri.getPage()-1)*15;
		scri.setPage(value);	// setPage 에 담아서 그 페이지를 BoardService_Mapper.xml 파일의 쿼리에서 받아서 쓰게씀 세팅
		
		//mybatis 연동
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
	//public int boardDelete(BoardVo bv) 일 경우
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
		
		int value = bsm.boardDelete(hm);	// persistance의 Mapper에는 HashMap 으로 mybatis 메소드 정의해주기
		
		return value;
	}
	
	//@Transactional	// 여러가지 작업단위를 한 작업으로 묶어주는 annotation -> 사용하면 프로젝트 실행 안됨
	@Override
	public int boardReply(BoardVo bv) {
		int depth = bv.getDepth();
		bsm.boardDepthUpdate(depth);
		
		int value = bsm.boardReply(bv);
		
		return value;
	}
}

