package com.my0803.myapp.service;

import java.util.ArrayList;

import com.my0803.myapp.domain.BoardVo;
import com.my0803.myapp.domain.SearchCriteria;
//�Խ��� �޼ҵ带 �����س��� �������̽�
public interface BoardService {
	
	public int boardInsert(BoardVo bv);		// BoardVo -> import
	public int boardTotalCount(SearchCriteria scri);
	public ArrayList<BoardVo> boardList(SearchCriteria scri);		// ArrayList, SearchCriteria -> import
	public BoardVo boardSelectOne(int bidx);
	public int boardCntUpdate(int bidx);
	public int boardModify(BoardVo bv);
	//public int boardDelete(BoardVo bv);
	public int boardDelete(int bidx, String pwd);
	public int boardReply(BoardVo bv);
}





