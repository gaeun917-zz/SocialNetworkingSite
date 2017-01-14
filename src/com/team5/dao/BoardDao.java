package com.team5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team5.dto.Board;
import com.team5.dto.BoardComment;


public class BoardDao {
	
	String dsn = "oracle";

	public int insertBoard(Board board) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newBoardNo = -1;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			StringBuilder sql = new StringBuilder(256);
			sql.append("INSERT INTO board ")
			   .append("(boardno, writer, content, member_Id) ")
			   .append("VALUES ")
			   .append("(BOARD_SEQ.nextVal, ?, ?, ?)");
			pstmt = conn.prepareStatement(sql.toString());
			//pstmt.setString(1, board.getTitle());
			pstmt.setString(1, board.getWriter());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3,  board.getMemberId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//?깉濡? ?궫?엯?맂 湲? 踰덊샇 議고쉶
			pstmt = 
				conn.prepareStatement("SELECT BOARD_SEQ.currVal FROM dual");
			rs = pstmt.executeQuery();
			rs.next();
			newBoardNo = rs.getInt(1);			
		} catch (Exception ex) {
			//濡ㅻ갚 
			try { conn.rollback(); } catch (Exception ex2) {}
			ex.printStackTrace();
		} finally {
			//6. ?뿰寃곕떕湲?
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}			
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return newBoardNo;
	}
	
	/*public int insertReply(Board board) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newBoardNo = -1;
		
		try {
			//?썝湲? ?젙蹂? 議고쉶
			Board parent = selectBoardByBoardNo(board.getBoardNo());
			
			conn = ConnectionHelper.getConnection(dsn);
			conn.setAutoCommit(false);//?듃?옖?옲?뀡 ?떆?옉

			StringBuilder sql = new StringBuilder(256);
			sql.append("UPDATE board ")
			   .append("	SET step = step + 1 ")
			   .append("WHERE groupno = ? AND step > ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, parent.getGroup());
			pstmt.setInt(2, parent.getStep());
			
			pstmt.executeUpdate();
			pstmt.close();
					
			sql.delete(0, sql.length());			
			sql.append("INSERT INTO board ")
			   .append("(boardno, writer, content) ")
			   .append("VALUES ")
			   .append("(board_sequence.nextVal, ?, ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, board.getBoardNo());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, parent.getContent());		//遺?紐④?怨? 媛숈? groupno
		
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//?깉濡? ?궫?엯?맂 湲? 踰덊샇 議고쉶
			pstmt = 
				conn.prepareStatement("SELECT board_sequence.currVal FROM dual");
			rs = pstmt.executeQuery();
			rs.next();
			newBoardNo = rs.getInt(1);
			
			conn.commit();//?듃?옖?옲?뀡 ?솗?젙
			
		} catch (Exception ex) {
			//?듃?옖?옲?뀡 痍⑥냼			
			try { conn.rollback(); } catch (Exception ex2) {}
			ex.printStackTrace();
		} finally {
			try { conn.setAutoCommit(true); } catch (Exception ex) {}
			//6. ?뿰寃곕떕湲?
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}			
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return newBoardNo;
	}*/
	
	///////////////////////////////////
	
	public List<Board> selectBoardList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;//議고쉶 寃곌낵?뿉 ?젒洹쇳븯?뒗 李몄“ 蹂??닔
		//?뜲?씠?꽣踰좎씠?뒪?쓽 ?뜲?씠?꽣瑜? ?씫?뼱?꽌 ???옣?븷 媛앹껜 而щ젆?뀡
		ArrayList<Board> boards = new ArrayList<Board>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			StringBuilder sql = new StringBuilder(512);
			sql.append("SELECT boardno, writer, ")
			   .append("read_cnt, regdate, deleted, content, member_id ")
			   .append("FROM board WHERE deleted = 0 ")
			   .append("ORDER BY boardno DESC ");
			  
			pstmt = conn.prepareStatement(sql.toString());			
			
			rs = pstmt.executeQuery();
		
		
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt(1));
				board.setWriter(rs.getString(2));
				//board.setContent(rs.getString(4));
				board.setReadCnt(rs.getInt(3));
				board.setRegDate(rs.getDate(4));
				board.setDeleted(rs.getBoolean(5));
				board.setContent(rs.getString(6));
				board.setMemberId(rs.getInt(7));
				
				List<BoardComment> comments = selectBoardCommentsByBoardNo(rs.getInt(1));
				board.setComments(comments);
				boards.add(board);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. ?뿰寃곕떕湲?
			if (rs != null) try { rs.close(); } catch (Exception ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
		
		return boards;
	}

	///////////////////////////////////////////////
	
	public Board selectBoardByBoardNo(int iBoardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null, rs2 = null;
		Board board = null;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			StringBuffer sql = new StringBuffer(300);
			sql.append("SELECT "); 
			sql.append("boardno, writer, content, ");
			sql.append("regdate, deleted ");
			sql.append("FROM board ");
			sql.append("WHERE boardno = ? AND deleted = '0'");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, iBoardNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new Board();
				board.setBoardNo(rs.getInt(1));
				board.setWriter(rs.getString(2));
				board.setContent(rs.getString(3));
				board.setRegDate(rs.getDate(4));
				board.setDeleted(rs.getBoolean(5));
				
				//?뙎湲? 議고쉶
				sql.delete(0, sql.length());//StringBuilder 鍮꾩슦湲?
				sql.append("SELECT commentno, boardno, ")
				   .append("	   writer, content, regdate ")
				   .append("FROM boardcomment ")
				   .append("WHERE boardno = ?");
				pstmt2 = conn.prepareStatement(sql.toString());
				pstmt2.setInt(1, iBoardNo);
				rs2 = pstmt2.executeQuery();
				//comment?뒗 array list濡? ?뿬?윭媛쒕?? 媛??졇?샂 
				ArrayList<BoardComment> comments 
					= new ArrayList<BoardComment>();
				while (rs2.next()) {
					BoardComment comment = new BoardComment();
					comment.setCommentNo(rs2.getInt(1));
					comment.setBoardNo(rs2.getInt(2));
					comment.setWriter(rs2.getString(3));
					comment.setContent(rs2.getString(4));
					comment.setRegDate(rs2.getDate(5));
					comments.add(comment);
				}
				board.setComments(comments);	
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { if (rs2 != null) rs2.close(); } catch (Exception ex) { }
			try { if (pstmt2 != null) pstmt2.close(); } catch (Exception ex) { }
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		return board;
	}
	
	
	public List<BoardComment> selectBoardCommentsByBoardNo(int iBoardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null, rs2 = null;
		ArrayList<BoardComment> comments = new ArrayList<BoardComment>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			StringBuffer sql = new StringBuffer(300);
			
				
				//?뙎湲? 議고쉶
				sql.delete(0, sql.length());//StringBuilder 鍮꾩슦湲?
//				sql.append("SELECT commentno, boardno, ")
//				   .append("	   m.name, content, regdate ")
//				   .append("FROM boardcomment bc inner join member m ")
//				   .append("on bc.member_id inner join m.member_id ")
//				   .append("WHERE boardno = ?");
				sql.append("SELECT commentno, boardno, ")
				  .append("writer, content, regdate, member_id ")
				  .append("from boardcomment ")
				  .append("WHERE boardno = ?");
				pstmt2 = conn.prepareStatement(sql.toString());
				pstmt2.setInt(1, iBoardNo);
				rs2 = pstmt2.executeQuery();
				//comment?뒗 array list濡? ?뿬?윭媛쒕?? 媛??졇?샂 
				while (rs2.next()) {
					BoardComment comment = new BoardComment();
					comment.setCommentNo(rs2.getInt(1));
					comment.setBoardNo(rs2.getInt(2));
					comment.setWriter(rs2.getString(3));
					comment.setContent(rs2.getString(4));
					comment.setRegDate(rs2.getDate(5));
					comment.setMemberId(rs2.getInt(6));
					comments.add(comment);
				}
				
				
					
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { if (rs2 != null) rs2.close(); } catch (Exception ex) { }
			try { if (pstmt2 != null) pstmt2.close(); } catch (Exception ex) { }
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		return comments;
	}

	

	/*public List<Board> selectBoardList2(int start, int last) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> boards = new ArrayList<Board>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			StringBuffer sql = new StringBuffer(500);
			//?쟾泥? 媛??졇?샂 
			sql.append("SELECT * ");
			sql.append("FROM ");
			
			sql.append("( ");
			sql.append("	SELECT "); 
			sql.append("		rownum idx, s.* "); 
			sql.append("	FROM ");
			// ?옒?씪?궪 遺?遺꾩쓣 吏??젙 
			sql.append("	( ");
			sql.append("		SELECT "); 
			sql.append("			boardno, title, writer, ");
			sql.append("			regdate, read_cnt, ");
			sql.append("			deleted, groupno, step, depth ");
			sql.append("		FROM ");
			sql.append("			board ");
//			sql.append("		WHERE ");
//			sql.append("			deleted = '0' ");
			sql.append("		ORDER BY groupno DESC, step ASC ");
			sql.append("	) s ");
			
			sql.append(") ");
			// 怨듭떇?뿉 ???엯?븯?뿬 ? 梨꾩썙?꽔?쓬 
			sql.append("WHERE idx >= ? AND idx < ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, last);
			rs = pstmt.executeQuery();			
			
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNo(rs.getInt(2));
				board.setTitle(rs.getString(3));
				board.setWriter(rs.getString(4));
				board.setRegDate(rs.getDate(5));
				board.setread_cnt(rs.getInt(6));
				board.setDeleted(rs.getBoolean(7));
				board.setGroup(rs.getInt(8));
				board.setStep(rs.getInt(9));
				board.setDepth(rs.getInt(10));
				
				boards.add(board);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
		
		return boards;
	}*/
	
	/*public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = ConnectionHelper.getConnection(dsn);
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);
			
		} catch (Exception ex) {
			count = 0;
			ex.printStackTrace();			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception ex) { }
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		return count;
	}*/
	
	public int deleteBoard(int number) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			conn = ConnectionHelper.getConnection(dsn);
			String sql =
				"UPDATE board " +
				"SET deleted = '1' " + 
				"WHERE boardno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			count = pstmt.executeUpdate();
		} catch (Exception ex) {
			
			count = 0;
			ex.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		
		return count;
	}
	
	public void updateBoardread_cnt(int number) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			String sql = 
				"UPDATE board " +
				"SET read_cnt = read_cnt + 1 " + 
				"WHERE boardno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
	}
	
	public int updateBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			conn = ConnectionHelper.getConnection(dsn);
			String sql = 
				"UPDATE board " +
				"SET content = ? " + 
				"WHERE boardno = ?";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, board.getTitle());
			pstmt.setString(1, board.getContent());
			pstmt.setInt(2, board.getBoardNo());
			count = pstmt.executeUpdate();
		} catch (Exception ex) {
			count = 0;
			ex.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (Exception ex) { }
			try { if (conn != null) conn.close(); } catch (Exception ex) { }
		}
		return count;
	}
		

	public void insertComment(BoardComment comment) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = 
				"INSERT INTO boardcomment " +
				"(commentno, boardno, writer, content, member_id) " +
				"VALUES (BOARDCOMMENT_SEQ.nextVal, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardNo());
			pstmt.setString(2, comment.getWriter());
			pstmt.setString(3, comment.getContent());
			pstmt.setInt(4, comment.getMemberId());

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
	}
	
	public int SelectCommentsWhereLastCommentNo() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int lastCommentNo=0;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = " SELECT commentno "
					+ "FROM boardcomment WHERE commentno = (SELECT max(commentno) FROM boardcomment )"; 
					
			pstmt = conn.prepareStatement(sql);
		
			rs=pstmt.executeQuery();
			
			rs.next();
				
			lastCommentNo=rs.getInt(1);
				
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
		
		return lastCommentNo;
	}
	
	public void updateComment(BoardComment comment) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = 
				"UPDATE boardcomment " +
				"SET content = ?" +
				"WHERE commentno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getCommentNo());

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
	}
	
	public void deleteComment(int commentNo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = 
				"DELETE FROM boardcomment " +				
				"WHERE commentno = ?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, commentNo);

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
	}
	

	public List<BoardComment> selectCommentsOfMyFriend(int myMemberId, int from) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ArrayList<BoardComment> comments = new ArrayList<>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = "SELECT commentno, content, regdate, boardno, member_id, like_cnt, writer " +
						 " FROM BOARDCOMMENT " +
						 " WHERE MEMBER_ID IN ( " +
						 " SELECT MEMBER_ONE FROM FRIEND WHERE MEMBER_TWO = ? " +
						 " UNION " +
						 " SELECT MEMBER_TWO FROM FRIEND WHERE MEMBER_ONE = ? " +
						 ") " +
						 " AND COMMENTNO > ? "; 
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMemberId);
			pstmt.setInt(2, myMemberId);
			pstmt.setInt(3, from);
		
			rs=pstmt.executeQuery();
			while (rs.next()) {
				BoardComment comment = new BoardComment();
				comment.setCommentNo(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setRegDate(rs.getDate(3));
				comment.setBoardNo(rs.getInt(4));
				comment.setMemberId(rs.getInt(5));
				comment.setLikeCnt(rs.getInt(6));
				comment.setWriter(rs.getString(7));
				
				comments.add(comment);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
		
		return comments;
	}

	public List<BoardComment> selectCommentsOfMyFriend2(int myMemberId, int count) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ArrayList<BoardComment> comments = new ArrayList<>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			
			String sql = "SELECT c.* " +
						 "FROM ( " +
						 " 	SELECT commentno, content, regdate, boardno, member_id, like_cnt, writer " +
						 " 	FROM BOARDCOMMENT " +
						 " 	WHERE MEMBER_ID IN ( " +
						 "   SELECT MEMBER_ONE FROM FRIEND WHERE MEMBER_TWO = ? " +
						 "   UNION " +
						 "   SELECT MEMBER_TWO FROM FRIEND WHERE MEMBER_ONE = ? " +
						 " 	) " +
						 " 	ORDER BY regdate DESC " +
						 ") c " +
						 "WHERE rownum <= ?";  
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMemberId);
			pstmt.setInt(2, myMemberId);
			pstmt.setInt(3, count);
		
			rs=pstmt.executeQuery();
			while (rs.next()) {
				BoardComment comment = new BoardComment();
				comment.setCommentNo(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setRegDate(rs.getDate(3));
				comment.setBoardNo(rs.getInt(4));
				comment.setMemberId(rs.getInt(5));
				comment.setLikeCnt(rs.getInt(6));
				comment.setWriter(rs.getString(7));
				
				comments.add(comment);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
		
		return comments;
	}
	
	

	
	

}