package com.team5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.UploadFile;

public class UploadDao2 {
	
	public List<Board> selectBoardList(int iMemberId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> boards = new ArrayList<Board>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			//
			String sql = "SELECT boardNo, MEMBER_ID from board where MEMBER_ID = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, iMemberId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();

				board.setBoardNo(rs.getInt(1));
				board.setMemberId(iMemberId);
				boards.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
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

		return boards;

	}

	public List<UploadFile> selectUploadfileList() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UploadFile> uploadfiles = new ArrayList<UploadFile>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT upfile_no, savedfile_name, userfile_name, boardno, member_id  from upload_file ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UploadFile uploadfile = new UploadFile();
				uploadfile.setUpfileNo(rs.getInt(1));
				uploadfile.setSavedFileName(rs.getString(2));
				uploadfile.setUserFileName(rs.getString(3));
				uploadfile.setBoardNo(rs.getInt(4));
				uploadfile.setMemberId(rs.getInt(5));
				
				uploadfiles.add(uploadfile);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
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

		return uploadfiles;

	}
	public void updateProfile_pic(Member member) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			
			String sql = 
				"UPDATE MEMBER " +
				"SET PROFILE_PIC = ?" +
				"WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getProfile_pic());
			pstmt.setInt(2, member.getMemberId());

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception ex) {}
			if (conn != null) try { conn.close(); } catch (Exception ex) {}
		}
	}

}
