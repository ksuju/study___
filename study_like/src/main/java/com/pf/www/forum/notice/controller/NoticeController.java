package com.pf.www.forum.notice.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
	
	@RequestMapping("/forum/notice/listPage.do")
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

		
		String title = params.get("title");
		String description = params.get("trumbowyg-demo");
		
		System.out.println("description======================>"+description);
		System.out.println("title======================>"+title);
		
		System.out.println("params.page=====================>"+params.get("page"));
		
		int start = (Integer.parseInt(params.get("page"))) * Integer.parseInt(params.get("size"));
		params.put("start", String.valueOf(start));
		
		//임시 하드코딩
		String boardTypeSeq = "1";
		params.put("boardTypeSeq", boardTypeSeq);
		String regMemberSeq = "45";
		params.put("regMemberSeq", regMemberSeq);
		//여기까지
		System.out.println("String.valueOf(start)========*****>>>"+String.valueOf(start));
		
		List<BoardDto> resultList = service.getList(params);
		
		System.out.println("params==========*=======>"+params);
		System.out.println("resultList==========*=======>"+resultList);
		
		int totalCnt = 0;
		if (!resultList.isEmpty()) {
		    totalCnt = resultList.get(0).getTotalCount();
		}
		
		System.out.println("totalCnt==========*=======>"+totalCnt);
				
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
		
		System.out.println("params===========++++++++==========>"+params);
		System.out.println("endPage============***=========>"+endPage);
		System.out.println("totalPage============***=========>"+totalPage);
		System.out.println("service.getList(params)=====================>"+ resultList);
		
		
		try {
			if(description != null && title != null && boardTypeSeq != null &&
					!(description.isEmpty() && title.isEmpty() && boardTypeSeq.isEmpty())) {
				System.out.println("게시글 작성 진입");
				service.getInsert(params);
				return new ModelAndView("redirect:/forum/notice/listPage.do");
			}
		} catch (NullPointerException e) {
			System.out.println("게시글 작성 에러");
		}
		
		String updateTitle = params.get("updateTitle");
		String updateBoardSeq = params.get("updateBoardSeq");
		
		try {
			if(updateTitle != null && updateBoardSeq!= null && !(updateBoardSeq.isEmpty() && updateTitle.isEmpty())) {
				System.out.println("게시글 수정 진입");
				service.updateBoard(params);
				return new ModelAndView("redirect:/forum/notice/listPage.do");
			}
		} catch (NullPointerException e) {
			System.out.println("게시글 수정 에러");
		}
		return mv;
	}
	
	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");
		
		return mv;
	}
	
	
	@RequestMapping("/forum/notice/updatePage.do")
	public ModelAndView updatePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/update");
		
		BoardDto board =  service.getBoard(params.get("boardSeq"));
		
		mv.addObject("board",board);
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params,
			@RequestParam int boardSeq) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");
		
		// boardSeq가 없으면??
		if(!params.containsKey("boardSeq")) {
			//code here
		}
		
		mv.addObject("board",service.getBoard(params.get("boardSeq")));
		
		// 비로그인된 상태, 보드타입 없는 상태에서 좋아요&싫어요 여부 확인할 수 있도록 임시로 하드코딩
		int ghest = -1;
		int boardTypeSeq = 1;
		// 여기까지 임시 하드코딩
		
		mv.addObject("ynLike",service.getExistLike(boardSeq, boardTypeSeq, ghest));
		mv.addObject("ynDisLike",service.getExistDisLike(boardSeq, boardTypeSeq, ghest));
		
		return mv;
	}

}
