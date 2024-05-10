package com.pf.www.forum.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
   

   public int update(HashMap<String, String> params) {
	   String sql = "UPDATE forum.board\r\n"
	   		+ "SET title=?, content=?\r\n"
	   		+ "WHERE board_seq=? AND board_type_seq=?;";
	   
	   Object[] args = {params.get("updateTitle"),params.get("trumbowyg-demo"),params.get("updateBoardSeq"),params.get("boardTypeSeq")};
	   
	   return update(sql,args);
   }
   
   public int insert(HashMap<String, String> params) {
	   String sql = "INSERT INTO forum.board "
			   + "(board_type_seq, title, content, reg_member_seq) "
			   + "VALUES(?, ?, ?, ?);";
	   Object[] args = {Integer.parseInt(params.get("boardTypeSeq")),params.get("title"),params.get("trumbowyg-demo"),params.get("regMemberSeq")};
	return update(sql,args);
   }
   
   public List<BoardDto> getList(HashMap<String, String> params) {
	    String sql = "SELECT b.board_seq, b.board_type_seq, b.title, b.content, b.hit, "
	               + "b.del_yn, b.reg_dtm, b.reg_member_seq, b.update_dtm, b.update_member_seq, bt.board_type_nm, m.member_id, "
	               + "(SELECT COUNT(*) FROM board) AS total_count " // 서브쿼리를 사용하여 게시글의 총 수를 가져옴
	               + "FROM board b "
	               + "JOIN board_type bt ON bt.board_type_seq = b.board_type_seq "
	               + "JOIN member m ON m.member_seq = b.reg_member_seq "
	               + "WHERE 1=1 "
	               + "ORDER BY b.board_seq DESC "
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
   
   public int addVote(int board_seq, int board_type_seq, int member_seq, String isLike) {
	   String sql = "INSERT INTO forum.NewTable\r\n"
	   		+ "(board_seq, board_type_seq, reg_dtm, is_like, member_seq)\r\n"
	   		+ "VALUES(?, ?, ?, ?, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'))";
	   Object[] args = {board_seq, board_type_seq, member_seq, isLike};
	   
	   return update(sql,args);
   }
   
   public int deleteVote(int board_seq, int board_type_seq, int member_seq, String isLike) {
	   String sql = "DELETE FROM forum.NewTable\r\n"
	   		+ "(WHERE board_seq = ? AND board_type_seq = ? AND member_seq = ? )\r\n"
	   		+ " AND is_like = ? ";
	   Object[] args = {board_seq, board_type_seq, member_seq, isLike};
	   
	   return update(sql,args);
   }
   
   public int updateVote(int board_seq, int board_type_seq, int member_seq, String isLike, String oldIsLIke) {
	   String sql = "UPDATE forum.NewTable\r\n"
	   		+ "SET is_like=?\r\n"
	   		+ "WHERE board_seq=? AND board_type_seq=? AND member_seq=?"
	   		+ "AND is_like = ?;";
	   	Object[] args = {board_seq, board_type_seq, member_seq, isLike, oldIsLIke};
	   return update(sql,args);
   }
   
   public int existsLike(int boardSeq, int boardTypeSeq, int memberSeq) {
	   String sql = "SELECT COUNT(1) FROM forum.board_like"
			   + " WHERE board_seq = ? "
			   + " AND board_type_seq = ? "
			   + " AND member_seq = ? ";
	   Object[] args = {boardSeq,boardTypeSeq,memberSeq};
	   return queryForObject(sql, Integer.class, args);
   }
   
   public int existsDisLike(int boardSeq, int boardTypeSeq, int memberSeq) {
	   String sql = "SELECT COUNT(1) FROM forum.board_dislike"
			   + " WHERE board_seq = ? "
			   + " AND board_type_seq = ? "
			   + " AND member_seq = ? ";
	   Object[] args = {boardSeq,boardTypeSeq,memberSeq};
	   return queryForObject(sql, Integer.class, args);
   }
   
   public int showLike(int boardSeq) {
	   String sql = "SELECT COUNT(1) FROM forum.board_like"
			   + " WHERE board_seq = ? ";
	   Object[] args = {boardSeq};
	   return queryForObject(sql, Integer.class, args);
   }
   
   public int showDisLike(int boardSeq) {
	   String sql = "SELECT COUNT(1) FROM forum.board_dislike"
			   + " WHERE board_seq = ? ";
	   Object[] args = {boardSeq};
	   return queryForObject(sql, Integer.class, args);
   }
   
   public int deleteLike (int boardSeq, int boardTypeSeq, int memberSeq) {
	   String sql = "DELETE FROM forum.board_like\r\n"
	   		+ "WHERE board_seq=? AND board_type_seq=? AND member_seq=?";
	   Object[] args = {boardSeq,boardTypeSeq,memberSeq};
	   return update(sql,args);
   }
   
   
   public int addLike(int boardSeq,int boardTypeSeq,int memberSeq,String ip) {
	      String sql = "INSERT INTO forum.board_like"
	            + "( board_seq, board_type_seq, member_seq, ip, reg_dtm)"
	            + "VALUES( ?, ?, ?, ?, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));";
	      Object[] args = {boardSeq,boardTypeSeq,memberSeq,ip};
	      return update(sql,args);
	   }
   
   
   public int addDisLike(int boardSeq,int boardTypeSeq,int memberSeq,String ip) {
	      String sql = "INSERT INTO forum.board_dislike"
	            + "( board_seq, board_type_seq, member_seq, ip, reg_dtm)"
	            + "VALUES( ?, ?, ?, ?, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));";
	      Object[] args = {boardSeq,boardTypeSeq,memberSeq,ip};
	      return update(sql,args);
	   }
   
   
   public int deleteDisLike (int boardSeq, int boardTypeSeq, int memberSeq) {
	   String sql = "DELETE FROM forum.board_dislike\r\n"
	   		+ "WHERE board_seq=? AND board_type_seq=? AND member_seq=?";
	   Object[] args = {boardSeq,boardTypeSeq,memberSeq};
	   return update(sql,args);
   }
}