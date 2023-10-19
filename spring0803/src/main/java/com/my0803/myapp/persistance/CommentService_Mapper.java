package com.my0803.myapp.persistance;

import java.util.ArrayList;

import com.my0803.myapp.domain.CommentVo;

//mybatis용 메소드 정의
public interface CommentService_Mapper {

	public ArrayList<CommentVo> commentSelectAll(int bidx);
	public int commentInsert(CommentVo cv);
	public int commentDelete(int cidx);
}




