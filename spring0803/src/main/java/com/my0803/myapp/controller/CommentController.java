package com.my0803.myapp.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.my0803.myapp.domain.CommentVo;
import com.my0803.myapp.service.CommentService;

@RestController		// @ResponseBody �� ���� ������ �ʿ䰡 ���� -> @Controller�� @ResponseBody ��ģ �� 
//@Controller
@RequestMapping(value="/comment")	// �����ο� ��Ī�Ǵ� �޼ҵ�
public class CommentController {
	
	@Autowired
	private CommentService cs;	
	
	//@ResponseBody 		// JSON ���·� ��ü�� �ް� ResponseBody annotation �� ��� -> ��ü�� ����
	@RequestMapping(value="/{bidx}/commentList.do")		// ���� ������ �ΰ� �� �ȿ� Ű���� �־���
	public JSONObject commentList(
			@PathVariable("bidx") int bidx		// bidx ���� @PathVariable �� ����Ͽ� ���� -> bidx �ϳ��� �ּҰ� �ϳ��� �������� �����ϰ���
			) {
		
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx);
			
		JSONObject js = new JSONObject();		// JSONObject import
		js.put("alist", alist);					// JSON�� alist �� �־��ֱ�

		return js;
	}
	
	@RequestMapping(value="/commentWrite.do",method=RequestMethod.POST)	// method=RequestMethod.POST : POST �������� �Ѿ�� cv�� ���� ���� �ްڴ�
	public JSONObject commentWrite(CommentVo cv) {	// commentWrite �� �Ѱ����� JSON ���·� �Ѱܼ� AJAX �� ����, CommentVo cv �� �޾Ƽ� ���ε�����
		
		//String str = null;
		int value = 0;
		//str = "{\"value\" : \""+value+"\"}";	// JSON�� Key���� Value ������ ��Ÿ�� -> Spring ������ ���̺귯�� �ٿ�޾Ƽ� �� �����ϰ� ��밡����
		value = cs.commentInsert(cv);
		
		JSONObject js = new JSONObject();	 // JSONObject import
		js.put("value", value); 	// JSON�� value �� �־��ֱ�
		
		
		return js;	// js�� Object Ÿ���̴� public �� String �� JSONObject �� �����ϱ�
	}
	
	@RequestMapping(value="/commentDelete.do")	
	public JSONObject commentDelete(int cidx) {	// commentDelete �� �Ѱ����� JSON ���·� �Ѱܼ� AJAX �� ����
		
		int value = cs.commentDelete(cidx);
		
		JSONObject js = new JSONObject();	 // JSONObject import
		js.put("value", value); 	// JSON�� value �� �־��ֱ�
		
		return js;	// js�� Object Ÿ���̴� public �� String �� JSONObject �� �����ϱ�
	}
}
