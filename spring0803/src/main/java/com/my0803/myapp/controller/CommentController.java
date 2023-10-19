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

@RestController		// @ResponseBody 를 굳이 선언할 필요가 없음 -> @Controller와 @ResponseBody 합친 것 
//@Controller
@RequestMapping(value="/comment")	// 가상경로와 매칭되는 메소드
public class CommentController {
	
	@Autowired
	private CommentService cs;	
	
	//@ResponseBody 		// JSON 형태로 객체를 받게 ResponseBody annotation 을 사용 -> 객체로 리턴
	@RequestMapping(value="/{bidx}/commentList.do")		// 변수 영역을 두고 그 안에 키값을 넣어줌
	public JSONObject commentList(
			@PathVariable("bidx") int bidx		// bidx 값을 @PathVariable 을 사용하여 추출 -> bidx 하나의 주소가 하나의 페이지를 실행하게함
			) {
		
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx);
			
		JSONObject js = new JSONObject();		// JSONObject import
		js.put("alist", alist);					// JSON에 alist 값 넣어주기

		return js;
	}
	
	@RequestMapping(value="/commentWrite.do",method=RequestMethod.POST)	// method=RequestMethod.POST : POST 형식으로 넘어온 cv에 대한 값을 받겠다
	public JSONObject commentWrite(CommentVo cv) {	// commentWrite 를 넘겼을때 JSON 형태로 넘겨서 AJAX 로 받음, CommentVo cv 로 받아서 바인딩해줌
		
		//String str = null;
		int value = 0;
		//str = "{\"value\" : \""+value+"\"}";	// JSON은 Key값과 Value 값으로 나타남 -> Spring 에서는 라이브러리 다운받아서 더 간편하게 사용가능함
		value = cs.commentInsert(cv);
		
		JSONObject js = new JSONObject();	 // JSONObject import
		js.put("value", value); 	// JSON에 value 값 넣어주기
		
		
		return js;	// js는 Object 타입이니 public 의 String 을 JSONObject 로 변경하기
	}
	
	@RequestMapping(value="/commentDelete.do")	
	public JSONObject commentDelete(int cidx) {	// commentDelete 를 넘겼을때 JSON 형태로 넘겨서 AJAX 로 받음
		
		int value = cs.commentDelete(cidx);
		
		JSONObject js = new JSONObject();	 // JSONObject import
		js.put("value", value); 	// JSON에 value 값 넣어주기
		
		return js;	// js는 Object 타입이니 public 의 String 을 JSONObject 로 변경하기
	}
}
