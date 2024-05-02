package com.pf.www.forum.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pf.www.forum.dao.BoardDao;
import com.pf.www.forum.dto.BoardDto;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public List<BoardDto> getList(HashMap<String, String> params) {
		
		return boardDao.getList(params);
	}
	
	
	public BoardDto getBoard(String boardSeq) {
		
		return boardDao.getBoard(boardSeq);
	}
}
