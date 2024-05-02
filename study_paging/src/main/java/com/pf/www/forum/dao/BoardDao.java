package com.pf.www.forum.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pf.www.forum.dto.BoardDto;

@Repository
public class BoardDao extends JdbcTemplate {
   @Autowired
   public void setDataSource(DataSource dataSource) {
      super.setDataSource(dataSource);
   }
   
   public List<BoardDto> getList(HashMap<String, String> params) {
	    String sql = "SELECT b.board_seq, b.board_type_seq, b.title, b.content, b.hit, "
	               + "b.del_yn, b.reg_dtm, b.reg_member_seq, b.update_dtm, b.update_member_seq, bt.board_type_nm, m.member_id, "
	               + "(SELECT COUNT(*) FROM board) AS total_count " // 서브쿼리를 사용하여 게시글의 총 수를 가져옴
	               + "FROM board b "
	               + "JOIN board_type bt ON bt.board_type_seq = b.board_type_seq "
	               + "JOIN member m ON m.member_seq = b.reg_member_seq "
	               + "WHERE 1=1 "
	               + "LIMIT ?, ?;";
	    Object[] args = {Integer.parseInt(params.get("start")), Integer.parseInt(params.get("size"))};

	    return query(sql, new BoardListMapper(), args);
	}
   
   public BoardDto getBoard(String boardSeq) {
	   String sql = "SELECT b.board_seq, b.board_type_seq, b.title, b.content, b.hit, "
               + "b.del_yn, b.reg_dtm, b.reg_member_seq, b.update_dtm, b.update_member_seq, bt.board_type_nm, m.member_id "
               + "FROM board b "
               + "JOIN board_type bt ON bt.board_type_seq = b.board_type_seq "
               + "JOIN member m ON m.member_seq = b.reg_member_seq "
               + "WHERE 1=1 "
               + "AND b.board_seq = ?;";
	   
	   Object[] args = {boardSeq};
	      
	   return queryForObject(sql, new BoardRowMapper(), args);
   }
   
   class BoardRowMapper implements RowMapper<BoardDto> {
	   @Override
	   public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		  BoardDto dto = new BoardDto();
		  
          dto = new BoardDto();
          dto.setBoardSeq(rs.getInt("board_seq"));
          dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
          dto.setBoardTypeNm(rs.getString("board_type_nm"));
          dto.setTitle(rs.getString("title"));
          dto.setContent(rs.getString("content"));
          dto.setHit(rs.getInt("hit"));
          dto.setDelYn(rs.getString("del_yn"));
          dto.setRegDtm(rs.getString("reg_dtm"));
          dto.setRegMemberSeq(rs.getInt("reg_member_seq"));
          dto.setMemberId(rs.getString("member_id"));
          dto.setUpdateDtm(rs.getString("update_dtm"));
          dto.setUpdateMemberSeq(rs.getInt("update_member_seq"));
          
          return dto;
	   }
   }
   
   class BoardListMapper implements ResultSetExtractor<List<BoardDto>> {

      @Override
      public List<BoardDto> extractData(ResultSet rs) throws SQLException {
         ArrayList<BoardDto> list = new ArrayList<>();
         BoardDto dto = null;
         while(rs.next()) {
            dto = new BoardDto();
            dto.setBoardSeq(rs.getInt("board_seq"));
            dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
            dto.setBoardTypeNm(rs.getString("board_type_nm"));
            dto.setTitle(rs.getString("title"));
            dto.setContent(rs.getString("content"));
            dto.setHit(rs.getInt("hit"));
            dto.setDelYn(rs.getString("del_yn"));
            dto.setRegDtm(rs.getString("reg_dtm"));
            dto.setRegMemberSeq(rs.getInt("reg_member_seq"));
            dto.setMemberId(rs.getString("member_id"));
            dto.setUpdateDtm(rs.getString("update_dtm"));
            dto.setUpdateMemberSeq(rs.getInt("update_member_seq"));
            dto.setTotalCount(rs.getInt("total_count"));
            
            list.add(dto);
         }
         
         return list;
      }
      
   }
}