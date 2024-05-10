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
	
	public int updateBoard(HashMap<String, String> params) {
		return boardDao.update(params);
	}
	
	public BoardDto getBoard(String boardSeq) {
		
		return boardDao.getBoard(boardSeq);
	}
	
	public int thumbUp(int boardSeq, int boardTypeSeq, int memberSeq, String ip) {
		// 있으면? 삭제
		int cnt = boardDao.existsLike(boardSeq, boardTypeSeq, memberSeq);
		int result = -1;
		if(cnt>0) {
			boardDao.deleteLike(boardSeq, boardTypeSeq, memberSeq);
			result = 0;
		} else {
			result = boardDao.addLike(boardSeq, boardTypeSeq, memberSeq, ip);
		}
		
		// 없을 때 썸업
		return result;
	}
	
	public int thumbDown(int boardSeq, int boardTypeSeq, int memberSeq, String ip) {
		
		// 있으면? 삭제
		int cnt = boardDao.existsDisLike(boardSeq, boardTypeSeq, memberSeq);
		int result = -1;
		if(cnt>0) {
			boardDao.deleteDisLike(boardSeq, boardTypeSeq, memberSeq);
			result = 0;
		} else {
			result = boardDao.addDisLike(boardSeq, boardTypeSeq, memberSeq, ip);
		}
		
		// 없을 때 썸다운
		return result;
	}
	
	
	public int getLike(int boardSeq) {
		return boardDao.showLike(boardSeq);
	}
	
	public int getDisLike(int boardSeq) {
		return boardDao.showDisLike(boardSeq);
	}
	
	public int getExistLike(int boardSeq, int boardTypeSeq, int memberSeq) {
		return boardDao.existsLike(boardSeq, boardTypeSeq, memberSeq);
	}
	
	public int getExistDisLike(int boardSeq, int boardTypeSeq, int memberSeq) {
		return boardDao.existsDisLike(boardSeq, boardTypeSeq, memberSeq);
	}
	
	public int getInsert(HashMap<String, String> params) {
		return boardDao.insert(params);
	}
}
