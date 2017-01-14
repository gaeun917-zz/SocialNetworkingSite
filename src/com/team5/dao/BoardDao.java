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
            sql.append("INSERT INTO board (boardno, writer, content, member_Id) ")
                    .append("VALUES (BOARD_SEQ.nextVal, ?, ?, ?)"); // auto-incriminate

            pstmt = conn.prepareStatement(sql.toString());// ?에 대응하는 값 찾기
            pstmt.setString(1, board.getWriter()); //Key:value
            pstmt.setString(2, board.getContent());
            pstmt.setInt(3, board.getMemberId());

            pstmt.executeUpdate(); //DML 실행할때 명령어 -> 실행된 레코드 수 반환, 0이면 update 실
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT BOARD_SEQ.currVal " +
                    "                          FROM dual");
            rs = pstmt.executeQuery();// SELECT문 실행할떄 명령어 -> ResultSet 반환
                                      // ResultSet은 SELECT의 데이터를 <table> 형태로 만들어줌
            rs.next();// ResultSet은 커서를 이동해서 데이터 긁는다->.last(), .first(), ... 커서 옮겨서 그 행의 데이터 긇음
                      // .next() : select된 레코드가 있는지 확인
            newBoardNo = rs.getInt(1);

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex2) {
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                pstmt.close();
            } catch (Exception ex) {
            }
            try {
                conn.close();
            } catch (Exception ex) {
            }
        }
        return newBoardNo;
    }

	 /* public int insertReply(Board board) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newBoardNo = -1;
		
		try {
			Board board1 = selectBoardByBoardNo(board.getBoardNo());
			
			conn = ConnectionHelper.getConnection(dsn);
			conn.setAutoCommit(false);

			StringBuilder sql = new StringBuilder(256);
			sql.append("UPDATE Board ")
			   .append("SET step = step + 1 ")
			   .append("WHERE groupno = ? AND step > ?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, board1.getGroup());
			pstmt.setInt(2, board1.getStep());
			
			pstmt.executeUpdate();
			pstmt.close();

			//////////////// sql?? ????? ???? ??????? ????? ???? ??
			sql.delete(0, sql.length());			
			sql.append("INSERT INTO board (boardno, writer, content) ")
			   .append("VALUES (board_sequence.nextVal, ?, ?)");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, board.getBoardNo());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board1.getContent());

			pstmt.executeUpdate();
			pstmt.close();

			////////////////
			pstmt = conn.prepareStatement(
			        "SELECT board_sequence.currVal FROM dual");
			rs = pstmt.executeQuery();
			rs.next();// result set
			newBoardNo = rs.getInt(1);
			
			conn.commit();
			
		} catch (Exception ex) {
			try {
			    conn.rollback();
			} catch (Exception ex2) {
			}
			ex.printStackTrace();
		} finally {
			try { conn.setAutoCommit(true); } catch (Exception ex) {}
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
        ResultSet rs = null;
        ArrayList<Board> boards = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection(dsn);
            StringBuilder sql = new StringBuilder(512);
                 sql.append("SELECT boardno, writer,read_cnt,regdate, " +
                                     " deleted, content, member_id ")
                    .append("FROM Board " +
                            "WHERE deleted = 0 ")
                    .append("ORDER BY boardno DESC ");

            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Board board = new Board();
                board.setBoardNo(rs.getInt(1));
                board.setWriter(rs.getString(2));
                board.setReadCnt(rs.getInt(3));
                board.setRegDate(rs.getDate(4));
                board.setDeleted(rs.getBoolean(5));
                board.setContent(rs.getString(6));
                board.setMemberId(rs.getInt(7));

                List<BoardComment> comments =
                        selectBoardCommentsByBoardNo(rs.getInt(1));
                board.setComments(comments);
                boards.add(board);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception ex) {
                }
            if (pstmt != null)
                try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null)
                try {
                conn.close();
            } catch (Exception ex) {
            }
        }
        return boards;
    }

    /////////??? ????? ???////////////////////

    public Board selectBoardByBoardNo(int iBoardNo) {
        Connection conn = null;
        PreparedStatement pstmt = null, pstmt2 = null;
        ResultSet rs = null, rs2 = null;
        Board board = null;

        try {
            conn = ConnectionHelper.getConnection(dsn);
            StringBuffer sql = new StringBuffer(300);
                         sql.append("SELECT boardno, writer, content, regdate, deleted ");
                         sql.append("FROM board ");
                         sql.append("WHERE boardno = ? AND deleted = '0'");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, iBoardNo);// parameter? iBoardNo??
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new Board();
                board.setBoardNo(rs.getInt(1));
                board.setWriter(rs.getString(2));
                board.setContent(rs.getString(3));
                board.setRegDate(rs.getDate(4));
                board.setDeleted(rs.getBoolean(5));

                sql.delete(0, sql.length());//StringBuilder? ??? StringBuffer? ??
                     sql.append("SELECT commentno, boardno, writer, content, regdate ")
                        .append("FROM boardcomment ")
                        .append("WHERE boardno = ?");
                pstmt2 = conn.prepareStatement(sql.toString());
                pstmt2.setInt(1, iBoardNo);
                rs2 = pstmt2.executeQuery();

                ArrayList<BoardComment> comments = new ArrayList<>();
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
                // Board? BoardComment? ?? ???? ??, sql??? ?? attribute????
                // Board?? ArrayList? comment?? method ?????
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs2 != null)
                    rs2.close();
            } catch (Exception ex) {
            }
            try {
                if (pstmt2 != null)
                    pstmt2.close();
            } catch (Exception ex) {
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception ex) {
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
            }
        }
        return board;
    }


    public List<BoardComment> selectBoardCommentsByBoardNo(int iBoardNo) {
        Connection conn = null;
        PreparedStatement pstmt = null, pstmt2 = null;
        ResultSet rs = null, rs2 = null;
        ArrayList<BoardComment> comments = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection(dsn);
            StringBuffer sql = new StringBuffer(300);

                        sql.delete(0, sql.length());
            //			sql.append("SELECT commentno, boardno, m.name, content, regdate")
            //			   .append("FROM boardcomment bc inner join member m ")
            //			   .append("ON bc.member_id inner join m.member_id ")
//				           .append("WHERE boardno = ?");
                sql.append( "SELECT commentno, boardno, writer, content, regdate, member_id ")
                    .append("FROM boardcomment ")
                    .append("WHERE boardno = ?");
            pstmt2 = conn.prepareStatement(sql.toString());
            pstmt2.setInt(1, iBoardNo);
            rs2 = pstmt2.executeQuery(); // Select문 일때
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
            try {
                if (rs2 != null)
                    rs2.close();
            } catch (Exception ex) {
            }
            try {
                if (pstmt2 != null)
                    pstmt2.close();
            } catch (Exception ex) {
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception ex) {
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
            }
        }
        return comments;
    }

	/*
	public List<Board> selectBoardList2(int start, int last) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> boards = new ArrayList<Board>();
		
		try {
			conn = ConnectionHelper.getConnection(dsn);
			StringBuffer sql = new StringBuffer(500);
                        sql.append("SELECT *                                         ");
                        sql.append("FROM ( SELECT rownum idx, s.*                    ");
                        sql.append("	   FROM ( SELECT *                           ");
                        sql.append("		      FROM board                         ");
            			sql.append("		      WHERE deleted = '0'                ");
                        sql.append("		      ORDER BY groupno DESC, step ASC ) s");
                        sql.append("      )                                          ");
			            sql.append("WHERE idx >= ? AND idx < ?                       ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, start); // idx >= start AND idx<last
			pstmt.setInt(2, last);
			rs = pstmt.executeQuery();			
			
			while (rs.next()) {
				  Board board = new Board(); // 1st column -> auto-incrimination
                        board.setBoardNo(rs.getInt(2));
                        board.setTitle(rs.getString(3));
                        board.setWriter(rs.getString(4));
                        board.setRegDate(rs.getDate(5));
                        board.setRead_cnt(rs.getInt(6));
                        board.setDeleted(rs.getBoolean(7));
                        board.setGroup(rs.getInt(8));
                        board.setStep(rs.getInt(9));
                        board.setDepth(rs.getInt(10));

				boards.add(board);
				//이걸 하려고.. ArrayList<Board>boards -> Board JOIN uploadFile.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null){
			    try {
			        pstmt.close();
			    } catch (Exception ex) {
			    }
			}
			if (conn != null){
			    try {
			        conn.close();
			    } catch (Exception ex) {
			    }
			}
		}
		return boards;
	}*/
	/*

    public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = ConnectionHelper.getConnection(dsn);
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();// SELECT 문에서 사용-> ResultSet 반환
			        // excuteUpdate() UPDATE,INSERT,DELECT 문에서 사용 -> 실행된 record 수 반환
			if (rs.next()) // ResultSet은 커서로 데이터를 가르킴
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

    public int deleteBoard(int iBoardNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            conn = ConnectionHelper.getConnection(dsn);
            // 얘는 StringBuilder/BufferBuilder를 사용하지 않음: get으로 데이터 받는게 없어서 그런가봄.
            String sql = "UPDATE board " + 
                         "SET deleted = '1' " +
                         "WHERE boardno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, iBoardNumber);
            count = pstmt.executeUpdate(); // return int counting its result row
        } catch (Exception ex) {
            count = 0;
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
            }
        }
        return count;
    }

    public void updateBoardread_cnt(int iBoardNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionHelper.getConnection(dsn);
            String sql = "UPDATE board " +
                         "SET read_cnt = read_cnt + 1 " +
                         "WHERE boardno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, iBoardNumber);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
            }
        }
    }

    public int updateBoard(Board board) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            conn = ConnectionHelper.getConnection(dsn);
            String sql = "UPDATE board " +
                         "SET content = ? " +
                         "WHERE boardno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getContent());
            pstmt.setInt(2, board.getBoardNo());
            count = pstmt.executeUpdate();
        } catch (Exception ex) {
            count = 0;
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
            }
        }
        return count;
    }


    public void insertComment(BoardComment boardComment) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionHelper.getConnection(dsn);

            String sql = "INSERT INTO BoardComment (commentno, boardno, writer, content, member_id) " +
                         "VALUES (BOARDCOMMENT_SEQ.nextVal, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardComment.getBoardNo());
            pstmt.setString(2, boardComment.getWriter());
            pstmt.setString(3, boardComment.getContent());
            pstmt.setInt(4, boardComment.getMemberId());

            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }
/*
    public int SelectCommentsWhereLastCommentNo() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int lastCommentNo = 0;

        try {
            conn = ConnectionHelper.getConnection(dsn);

            String sql = "SELECT commentno "
                        +"FROM BoardComment" +
                         "WHERE commentno = (SELECT max(commentno) " +
                                            "FROM BoardComment)";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            lastCommentNo = rs.getInt(1);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
        return lastCommentNo;
    }
*/

    public void updateComment(BoardComment boardComment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionHelper.getConnection(dsn);

            String sql = "UPDATE boardcomment " +
                         "SET content = ?" +
                         "WHERE commentno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, boardComment.getContent());
            pstmt.setInt(2, boardComment.getCommentNo());

            pstmt.executeUpdate();// SELECT일때는 ResultSet으로 get return value.
                                 // update일때는 int 받는데 여기서는 return value 필요없으므로 void
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }

    public void deleteComment(int commentNo) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionHelper.getConnection(dsn);

            String sql = "DELETE FROM boardcomment " +
                         "WHERE commentno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentNo);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }

/*
    public List<BoardComment> selectCommentsOfMyFriend(int myMemberId, int from) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardComment> comments = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection(dsn);
            String sql = "SELECT commentno, content, regdate, boardno, member_id, like_cnt, writer " +
                        " FROM BOARDCOMMENT " +
                        " WHERE MEMBER_ID IN (SELECT MEMBER_ONE " +
                    "                         FROM FRIEND " +
                    "                         WHERE MEMBER_TWO = ? " +
                        "                     UNION " +
                        "                     SELECT MEMBER_TWO " +
                    "                         FROM FRIEND " +
                    "                         WHERE MEMBER_ONE = ? " +
                        "                    ) " +
                        " AND COMMENTNO > ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, myMemberId);
            pstmt.setInt(2, myMemberId);
            pstmt.setInt(3, from);

            rs = pstmt.executeQuery();
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
            if (rs != null) try {
                rs.close();
            } catch (Exception ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
        return comments;
    }
*/
    public List<BoardComment> selectCommentsOfMyFriend2(int myMemberId, int count) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardComment> comments = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection(dsn);
            String sql= "SELECT c.* " +
                        "FROM ( " +
                        " 	    SELECT commentno, content, regdate, boardno, member_id, like_cnt, writer " +
                        " 	    FROM BOARDCOMMENT " +
                        " 	    WHERE MEMBER_ID IN (SELECT MEMBER_ONE " +
                        "                           FROM FRIEND " +
                        "                           WHERE MEMBER_TWO = ? " +
                        "                           UNION " +
                        "                           SELECT MEMBER_TWO " +
                        "                           FROM FRIEND " +
                        "                           WHERE MEMBER_ONE = ? " +
                        " 	                        ) " +
                        " 	    ORDER BY regdate DESC) c " +
                        "WHERE rownum <= ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, myMemberId);
            pstmt.setInt(2, myMemberId);
            pstmt.setInt(3, count);

            rs = pstmt.executeQuery();
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
            if (rs != null) try {
                rs.close();
            } catch (Exception ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception ex) {
            }
        }
        return comments;
    }
}