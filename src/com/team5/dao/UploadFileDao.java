package com.team5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team5.dto.Member;

public class UploadFileDao {

	public List<Member> selectAllMembers() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> members = new ArrayList<>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_Id, name, passwd, email, gender, birthday, profile_pic " +
						 "FROM member ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
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