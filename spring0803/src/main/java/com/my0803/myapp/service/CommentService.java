package com.my0803.myapp.service;

import java.util.ArrayList;

import com.my0803.myapp.domain.CommentVo;
//게시판 메소드를 정의해놓은 인터페이스
public interface CommentService {
	
	public ArrayList<CommentVo> commentSelectAll(int bidx);	// comment 를 list 형식으로 가져옴 -> bidx를 int 타입으로 넘겨받음
	public int commentInsert(CommentVo cv);
	public int commentDelete(int cidx);
}
