package com.pf.www.index.controller.rest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pf.www.forum.service.BoardService;

@RestController
public class RestNoticeController {
	  
	  	@Autowired
	  	BoardService service;
	  	
		@GetMapping("/forum/notice/thumb-up.do")
		@ResponseBody
		public int thumbUp(
				@RequestParam("boardSeq") int boardSeq,
				@RequestParam("boardTypeSeq") int boardTypeSeq,
				HttpServletRequest request) {
			HttpSession session = request.getSession();
			
			
			int memberSeq = -1;
			
			try {
				memberSeq = (int) session.getAttribute("memberSeq");
			} catch (NullPointerException nep) {
				System.out.println("사용자 없음");
				// exception 던지거나 로그인 페이지로
			}
			String ip = request.getRemoteAddr(); 
			
			return service.thumbUp(boardSeq, boardTypeSeq, memberSeq, ip);
			
		}
		
		//왜 up하고 down하고 getmapping 주소가 다름?
		//레스트는 원래 그럼
		
		@GetMapping("/forum/notice/{boardTypeSeq}/{boardSeq}/thumb-down.do")
		@ResponseBody
		public int thumbDown(
				@PathVariable("boardSeq") int boardSeq,
				@PathVariable("boardTypeSeq") int boardTypeSeq,
				@RequestParam("voteType") String voteType,
				@RequestParam HashMap<String, String> voteValue,
				HttpServletRequest request) {
			HttpSession session = request.getSession();
			
			int memberSeq = -1;
			
			try {
				memberSeq = (int) session.getAttribute("memberSeq");
			} catch (NullPointerException nep) {
				System.out.println("사용자 없음");
				// exception 던지거나 로그인 페이지로
			}
			
			String ip = request.getRemoteAddr();
			
			return service.thumbDown(boardSeq, boardTypeSeq, memberSeq, ip);
			
		}

}
