package com.team5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team5.dto.Member;
import com.team5.dto.MemberInfo;

public class MemberDao {

	public int insertMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newMemberId = -1;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "INSERT INTO member " + "(member_Id, name, email, passwd, gender, birthday ) "
					+ "VALUES (MEMBER_SEQ.nextVal, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getGender());
			pstmt.setString(5, member.getBirth());
			pstmt.executeUpdate();

			pstmt.close();

			// 새로 삽입된 멤버아이디 조회
			pstmt = conn.prepareStatement("SELECT MEMBER_SEQ.currVal FROM dual");
			rs = pstmt.executeQuery();
			rs.next();
			newMemberId = rs.getInt(1);

			/*String sql2 = "INSERT INTO member_info " + "(member_id) VALUES (?)";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, newMemberId);
			pstmt.executeUpdate();*/

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return newMemberId;
	}
	public List<Member> selectAllMembers() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> members = new ArrayList<>();
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_Id, name, passwd, email, gender, profilePic, birthday, regDate "
					+ "FROM member";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setProfilePic(rs.getString(6));
				member.setBirth(rs.getString(7));
				member.setRegDate(rs.getDate(8));
				members.add(member);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			} //
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return members;
	}

	public ArrayList<String> getNameListByNameAndSchoolAndLocation(String search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> nameList = new ArrayList<String>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT name, member_id FROM member "
					+ "WHERE name LIKE ? or member_id in (SELECT member_id FROM member_info WHERE highschool = ? or university = ? or location = ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search + "%");
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			pstmt.setString(4, search);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				nameList.add(rs.getString(1));
				
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
		return nameList;
	}

	public ArrayList<Member> getMemberListByNameAndSchoolAndLocation(String search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		ArrayList<Member> members = new ArrayList<Member>();
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_id, name, passwd, email, gender, birthday FROM member "
					+ "WHERE name LIKE ? or member_id in (SELECT member_id FROM member_info WHERE highschool = ? or university = ? or location = ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search + "%");
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			pstmt.setString(4, search);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member=new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
				members.add(member);
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
		return members;
	}

	
	
	public Member selectMemberById(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT member_Id, name, passwd, email, gender, birthday, profile_pic " + "FROM member "
					+ "WHERE member_Id = ?";// primary key
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			if (rs.next()) { // 단일 결과 검색이기때문에 if
				member = new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
				member.setProfile_pic(rs.getString(7));
			}

		} catch (Exception ex) {

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

		return member;
	}

	public MemberInfo selectMemberInfoById(int memberid) {
		System.out.println(memberid);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberInfo memberinfo = new MemberInfo();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT location, highschool, university, phone, created_date " 
							+ "FROM member_info "
							+ "WHERE member_id = ? ";// primary key
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberid);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			if (rs.next()) { // 단일 결과 검색이기때문에 if
				

				memberinfo.setLocation(rs.getString(1));
				System.out.println(rs.getString(1));
				memberinfo.setHighschool(rs.getString(2));
				memberinfo.setUniversity(rs.getString(3));
				memberinfo.setPhone(rs.getString(4));
				memberinfo.setCreateDate(rs.getDate(5));
				
			}

		} catch (Exception ex) {

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
		System.out.println(memberinfo.getHighschool());
		return memberinfo;
	}

	public Member selectMemberByEmailAndPasswd(String email, String passwd) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT member_id ,name, passwd, email, gender, birthday, profile_pic " + "FROM member "
					+ "WHERE email = ? AND passwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, passwd);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			if (rs.next()) {
				member = new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
				member.setProfile_pic(rs.getString(7));
			}

		} catch (Exception ex) {

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
		System.out.println("no error in dao");
		return member;// null or 조회된 결과를 저장한 인스턴스 참조
	}

	public ArrayList<String> friendRequest(String friendRequestName) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT name FROM member WHERE name = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, friendRequestName);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				idList.add(rs.getString(1));

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
		return idList;

	}



	public void updateFriendAdd(String requestSender, Member myMember) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {			
			conn = ConnectionHelper.getConnection("oracle");
			//status==0인 상태는 친구요청을 한 상태이고 status==1인 상태는 친구추가를 수락한 상태이다
			//member_one은 현재 접속한 유저에 member_id이고 member_two는 친구추가요청을 한 친구 member_id이다.
			String sql = 
				"INSERT INTO friend VALUES(0, sysdate, ?, ?, friend_seq.nextval)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());//member_one 요청을 보낸사람
			pstmt.setString(2, requestSender);//member_two 요청을 받는사람
			pstmt.executeUpdate();
						
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { ex.printStackTrace();}
			try { conn.close(); } catch (Exception ex) {ex.printStackTrace(); }
		}	
		return;
	}	
	
/*public boolean SelectFriendByMemberAndStatusZero(String requestSender, Member myMember) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean confirm=false;
		
		try {			
			conn = ConnectionHelper.getConnection("oracle");
			//status==0인 상태는 친구요청을 한 상태이고 status==1인 상태는 친구추가를 수락한 상태이다
			//member_one은 현재 접속한 유저에 member_id이고 member_two는 친구추가요청을 한 친구 member_id이다.
			String sql = 
				"SELECT friend_id FROM friend WHERE status = 0 AND member_one = ? member_two = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());//member_one 요청을 보낸사람
			pstmt.setString(2, requestSender);//member_two 요청을 받는사람
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				confirm=true;
			}
		
		} catch(NullPointerException ex){
			confirm=false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			try { pstmt.close(); } catch (Exception ex) { ex.printStackTrace();}
			try { conn.close(); } catch (Exception ex) {ex.printStackTrace(); }
			try { rs.close(); } catch (Exception ex) {ex.printStackTrace(); }
			
			
		}	
		return confirm;
	}	*/
	
public void updateFriendAddFinally(String addByFriendMemberId, Member myMember) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {			
			conn = ConnectionHelper.getConnection("oracle");
			//status==0인 상태는 친구요청을 한 상태이고 status==1인 상태는 친구추가를 수락한 상태이다
			//member_one은 현재 접속한 유저에 member_id이고 member_two는 친구추가요청을 한 친구 member_id이다.
			String sql = 
				"UPDATE friend SET status = 1 WHERE member_two = ? "
				+ "AND member_one = ? AND status = 0 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());// member_two 요청을 받는사람
			pstmt.setString(2, addByFriendMemberId);//member_one 요청을 보낸사람   
			pstmt.executeUpdate();
						
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { ex.printStackTrace();}
			try { conn.close(); } catch (Exception ex) { ex.printStackTrace();}
		}	
		return;
	}	
		
		
	public void modifyPassword(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;

	
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql1 = "UPDATE member " + "SET passwd = ? " + "WHERE member_id = ?";

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, member.getPasswd());
			pstmt.setInt(2, member.getMemberId());
			pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}


	
public void memberInput(MemberInfo memberinfo, int memberId) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {			
		conn = ConnectionHelper.getConnection("oracle");
		
		String sql = 
			"UPDATE member_info " +
			"SET highschool = ?, university = ?, location = ?, phone = ?, created_date = sysdate " +
			"WHERE member_id = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memberinfo.getHighschool());
		pstmt.setString(2, memberinfo.getUniversity());
		pstmt.setString(3, memberinfo.getLocation());
		pstmt.setString(4, memberinfo.getPhone());
		pstmt.setInt(5, memberId);
		
		pstmt.executeUpdate();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		try {
			pstmt.close();
		} catch (Exception ex) {
		}
		try {
			conn.close();
		} catch (Exception ex) {
		}
	}

	
	
}
	


public Date modifyUser(MemberInfo memberinfo) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Date created_date=null;
	
	try {
		conn = ConnectionHelper.getConnection("oracle");

		String sql =  "INSERT INTO member_info " 
					+ "( highschool, university, location, phone, member_id, CREATED_DATE ) "
					+ "VALUES (?, ?, ?, ?, ?, sysdate)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memberinfo.getHighschool());
		pstmt.setString(2, memberinfo.getUniversity());
		pstmt.setString(3, memberinfo.getLocation());
		pstmt.setString(4, memberinfo.getPhone());
		pstmt.setInt(5, memberinfo.getMemberId());
		pstmt.executeUpdate(); 
		
		pstmt.close(); 

		String sql2 =  "select CREATED_DATE FROM member_info WHERE member_id = ? ";
		pstmt = conn.prepareStatement(sql2);
		pstmt.setInt(1, memberinfo.getMemberId());
		rs = pstmt.executeQuery();
		rs.next();
		created_date = rs.getDate(1);
		
		
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
	return created_date;

}

	public int updateMemberInfo(MemberInfo memberinfo, int memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count=0;
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "UPDATE member_info "
					+ "SET highschool = ?, university = ?, location = ?, phone = ?, created_date = sysdate "
					+ "WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberinfo.getHighschool());
			pstmt.setString(2, memberinfo.getUniversity());
			pstmt.setString(3, memberinfo.getLocation());
			pstmt.setString(4, memberinfo.getPhone());
			pstmt.setInt(5, memberId);

			count = pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}

	public ArrayList<MemberInfo> memberInfosByMemberTwoAndStatus(Member myMember, int status) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberInfos=new ArrayList<MemberInfo>();
		try {
			
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT location, highschool, phone, university, member_id, created_date "
					   + "FROM member_info WHERE member_id in "
					   + "(SELECT  member_one FROM friend WHERE member_two = ? AND status = ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			while (rs.next()) { // 단일 결과 검색이기때문에 if
				MemberInfo memberInfo=new MemberInfo();
				memberInfo.setLocation(rs.getString(1));
				memberInfo.setHighschool(rs.getString(2));
				memberInfo.setPhone(rs.getString(3));
				memberInfo.setUniversity(rs.getString(4));
				memberInfo.setMemberId(rs.getInt(5));
				memberInfo.setCreateDate(rs.getDate(6));
							
				memberInfos.add(memberInfo);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return memberInfos;
		
	}
	
public ArrayList<MemberInfo> memberInfosByMemberOneAndStatus(Member myMember, int status) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberInfos=new ArrayList<MemberInfo>();
		try {
			
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT location, highschool, phone, university, member_id, created_date "
					   + "FROM member_info WHERE member_id in "
					   + "(SELECT  member_two FROM friend WHERE member_one = ? AND status = ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			while (rs.next()) { // 단일 결과 검색이기때문에 if
				MemberInfo memberInfo=new MemberInfo();
				memberInfo.setLocation(rs.getString(1));
				memberInfo.setHighschool(rs.getString(2));
				memberInfo.setPhone(rs.getString(3));
				memberInfo.setUniversity(rs.getString(4));
				memberInfo.setMemberId(rs.getInt(5));
				memberInfo.setCreateDate(rs.getDate(6));
							
				memberInfos.add(memberInfo);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return memberInfos;
		
	}

	public ArrayList<Member> membersByMemberTwoAndStatus(Member myMember, int status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> members=new ArrayList<Member>();
		try {
			
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT member_id, name, passwd, email, gender, birthday "
					   + "FROM member WHERE member_id in "
					   + "(SELECT member_one FROM friend WHERE member_two = ?  AND status = ? )";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			while (rs.next()) { // 단일 결과 검색이기때문에 if
				Member member=new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
							
				members.add(member);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return members;
	}

	public ArrayList<Member> membersByMemberOneAndStatus(Member myMember, int status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> members=new ArrayList<Member>();
		try {
			
			conn = ConnectionHelper.getConnection("oracle");

			// 3. 명령 생성
			String sql = "SELECT member_id, name, passwd, email, gender, birthday "
					   + "FROM member WHERE member_id in "
					   + "(SELECT member_two FROM friend WHERE member_one = ? AND status = ? )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);
			// 4. 명령 실행
			rs = pstmt.executeQuery();
			// 5. 조회 결과를 처리 (SELECT QUERY인 경우)
			while (rs.next()) { // 단일 결과 검색이기때문에 if
				Member member=new Member();
				member.setMemberId(rs.getInt(1));
				member.setName(rs.getString(2));
				member.setPasswd(rs.getString(3));
				member.setEmail(rs.getString(4));
				member.setGender(rs.getString(5));
				member.setBirth(rs.getString(6));
							
				members.add(member);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return members;
	}
	



}
