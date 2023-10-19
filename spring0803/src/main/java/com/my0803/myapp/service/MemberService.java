package com.my0803.myapp.service;

import java.util.ArrayList;

import com.my0803.myapp.domain.MemberVo;
//interface : ������ �� �ִ� ȭ���̳� ���
public interface MemberService {
	// �̿ϼ��� �޼ҵ�� �����Ǿ� �ִ� Ŭ������ �ƴ� Ŭ����
	// �����ϸ� ���� Ȯ���ڰ� .class ������ ��� �뵵�� Ŭ������ �ƴϴ�.
	// ���������� ����� �޼ҵ� �̸��� �����ϴ� �� : �޼ҵ� ����

	public int memberInsert(MemberVo mv);		// MemberVo -> ������import
	
	public MemberVo memberLogin(String memberId, String memberPwd);
	
	public MemberVo memberLogin(String memberId);	// Login2�� ������ص� �ǳ�...?
	
	public int memberIdCheck(String memberId);
	
	public ArrayList<MemberVo> memberList(); 	//ArrayList -> ������import
	
}






