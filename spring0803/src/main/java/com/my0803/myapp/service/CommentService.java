package com.my0803.myapp.service;

import java.util.ArrayList;

import com.my0803.myapp.domain.CommentVo;
//�Խ��� �޼ҵ带 �����س��� �������̽�
public interface CommentService {
	
	public ArrayList<CommentVo> commentSelectAll(int bidx);	// comment �� list �������� ������ -> bidx�� int Ÿ������ �Ѱܹ���
	public int commentInsert(CommentVo cv);
	public int commentDelete(int cidx);
}
