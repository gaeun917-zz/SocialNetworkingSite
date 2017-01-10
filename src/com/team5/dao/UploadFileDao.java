package com.team5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.team5.dto.Board;
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.UploadFile;


public class UploadFileDao {
	
public void insertUploadFile(UploadFile file) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"INSERT INTO UPLOAD_FILE " + 
				"(upfile_no, savedfile_name, userfile_name, member_id ) " +
				"VALUES (UPLOAD_FILE_seq.nextVal, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, file.getSavedFileName());
			pstmt.setString(2, file.getUserFileName());
		
			pstmt.setInt(3, file.getMemberId());
			
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}		
		
	}
public List<Member> selectAllMembers() {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Member> members = new ArrayList<>();
	Member member = null;
	try {			
		conn = ConnectionHelper.getConnection("oracle");
		


		//////////////////////////////////////////////////////////////////
			String sql = "SELECT member_Id, name, passwd, email, gender, birthday, profile_pic " + "FROM member ";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			while(rs.next()) { // 단일 결과 검색이기때문에 if
				member = new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
				member.setProfile_pic(rs.getString(7));
				members.add(member);
		
		
		}

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		try { rs.close(); } catch (Exception ex) {}//
		try { pstmt.close(); } catch (Exception ex) {}
		try { conn.close(); } catch (Exception ex) {}			
	}
	
	return members;
}
	
}