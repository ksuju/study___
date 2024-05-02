package com.pf.www.forum.notice.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pf.www.forum.dto.BoardDto;
import com.pf.www.forum.service.BoardService;

@Controller
public class NoticeController {
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/forum//notice/listPage.do")
	public ModelAndView listPage(@RequestParam HashMap<String, String> params) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/list");
		if(!params.containsKey("page")) {
			params.put("page", "0");
		}
		if (!params.containsKey("size")) {
			params.put("size", "10");
		}
		
		System.out.println("params.page=====================>"+params.get("page"));
		
		int start = (Integer.parseInt(params.get("page"))) * Integer.parseInt(params.get("size"));
		params.put("start", String.valueOf(start));
		
		System.out.println("String.valueOf(start)========*****>>>"+String.valueOf(start));
		
		List<BoardDto> resultList = service.getList(params);
		
		System.out.println("params==========*=======>"+params);
		System.out.println("resultList==========*=======>"+resultList);
		
		int totalCnt = 0;
		if (!resultList.isEmpty()) {
		    totalCnt = resultList.get(0).getTotalCount();
		}
				
		int navSize = 10; // params에 이미 size라는 키로 들어가있음
		int beginPage = (Integer.parseInt(params.get("page")) / navSize) * navSize;
		
		int totalPage = (int)Math.ceil((double)totalCnt/navSize);
		
		int endPage = (navSize + beginPage -1)<totalPage?(navSize + beginPage -1):totalPage-1;
		//int endPage = (navSize + beginPage -1);
		
		
		params.put("beginPage", String.valueOf(beginPage));
		
		mv.addObject("list",resultList);
		mv.addObject("totalPage",totalPage);
		mv.addObject("endPage",endPage);
		mv.addObject("paging", params);
		
		System.out.println("params=====================>"+params);
		System.out.println("endPage============***=========>"+endPage);
		System.out.println("totalPage============***=========>"+totalPage);
		System.out.println("service.getList(params)=====================>"+ resultList);
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");
		
		// boardSeq가 없으면??
		if(!params.containsKey("boardSeq")) {
			//code here
		}
		
		mv.addObject("board",service.getBoard(params.get("boardSeq")));
		return mv;
	}

}
