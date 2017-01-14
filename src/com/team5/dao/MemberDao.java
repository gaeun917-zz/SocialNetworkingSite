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

			String sql =// db에서 만든 attribute name,  get/set은 DTO에서 설정한 attribtue: 둘이 같게 하는게 최상
					  "INSERT INTO member (member_Id, name, email, passwd, gender, birthday ) "
					+ "VALUES (MEMBER_SEQ.nextVal, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getGender());
			pstmt.setString(5, member.getBirth());
			
			pstmt.executeUpdate();
			pstmt.close();

			// insert 후에 select this new memeber's member_id
			pstmt = conn.prepareStatement("SELECT MEMBER_SEQ.currVal " +
											  "FROM dual");
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
/*
	public ArrayList<String> getNameListByNameAndSchoolAndLocation(String search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> nameList = new ArrayList<>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT name, member_id " +
						 "FROM member " +
					     "WHERE name LIKE ? OR member_id IN (SELECT member_id " +
						 "								     FROM member_info " +
						 "								     WHERE highschool = ? OR university = ? OR location = ?)";

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
*/

	public ArrayList<Member> getMemberListByNameAndSchoolAndLocation(String search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> members = new ArrayList<>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_id, name, passwd, email, gender, birthday " +
					     "FROM member "+
					     "WHERE name LIKE ? OR member_id IN (SELECT member_id " +
					"										 FROM member_info " +
					"										 WHERE highschool = ? OR university = ? OR location = ?)";

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

			String sql = "SELECT member_Id, name, passwd, email, gender, birthday, profile_pic " +
						 "FROM member "
					   + "WHERE member_Id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
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

	
	public MemberInfo selectMemberInfoById(int memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberInfo memberinfo = new MemberInfo();

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT location, highschool, university, phone, created_date " 
						+"FROM member_info "
						+"WHERE member_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberinfo.setLocation(rs.getString(1));
				//System.out.println(rs.getString(1));
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
		//System.out.println(memberinfo.getHighschool());
		return memberinfo;
	}

	
	public Member selectMemberByEmailAndPasswd(String email, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_id ,name, passwd, email, gender, birthday, profile_pic " + 
						 "FROM member " +
					 	 "WHERE email = ? AND passwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
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
		System.out.println("no error in DAO");
		return member;// null or 조회된 결과를 저장한 인스턴스 참조
	}

	
	public ArrayList<String> friendRequest(String friendRequestName) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> idList = new ArrayList<String>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT name " +
						 "FROM member " +
						 "WHERE name = ? ";
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

	
	
	public void updateFriendAdd(String requestReceiver, Member requestSender) {
		Connection conn = null; //왜 스트링으로 받지?
		PreparedStatement pstmt = null;
		
		try {
			// status == 0 :Requesting friend 친구요청,
			// status == 1:accepting friend 친구수락
			// member_one: loginuser 친구요청보냄(requestSender)
			// member_two: member_id that got request 친구요청받음 (requestReceiver)
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "INSERT INTO friend " +
						 "VALUES(0, sysdate, ?, ?, friend_seq.nextval)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, requestSender.getMemberId());//member_one 요청 보낸사람
			pstmt.setString(2, requestReceiver);//member_two 요청 받는사람

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { ex.printStackTrace();}
			try { conn.close(); } catch (Exception ex) {ex.printStackTrace(); }
		}	
		return;
	}	
	
/*
	public boolean SelectFriendByMemberAndStatusZero(String requestReceiver, Member requestSender) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean confirm=false;
		
		try {			
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT friend_id " +
					     "FROM friend " +
					     "WHERE status = 0 AND member_one = ? member_two = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requestSender.getMemberId());//member_one 요청을 보낸사람
			pstmt.setString(2, requestReceiver);//member_two 요청을 받는사람

			rs = pstmt.executeQuery();
			if(rs.next()){
				confirm = true;
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
	}
	*/
	
	public void updateFriendAddFinally(String addByFriendMemberId, Member myMember) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {			
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "UPDATE friend " +
					"	  SET status = 1 " +
					"	  WHERE member_two = ? AND member_one = ? AND status = 0 ";

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

			String sql1 = "UPDATE member " +
						  "SET passwd = ? " +
					 	  "WHERE member_id = ?";

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

/*
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
*/


	public Date modifyUser(MemberInfo memberinfo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Date created_date=null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			// 가입할때 sysdate가 추가됨
			String sql1 =  "INSERT INTO member_info ( highschool, university, location, phone, member_id, CREATED_DATE ) "
						+  "VALUES (?, ?, ?, ?, ?, sysdate)";

			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, memberinfo.getHighschool());
			pstmt.setString(2, memberinfo.getUniversity());
			pstmt.setString(3, memberinfo.getLocation());
			pstmt.setString(4, memberinfo.getPhone());
			pstmt.setInt(5, memberinfo.getMemberId());

			pstmt.executeUpdate();
			pstmt.close(); // execute 후에 pstmt에 set된 데이터 필요없음.
						  // 가입날짜를 get하기 위해 사용될 것이기 때문에 닫아줘야함. clean slate

			String sql2 =  "select CREATED_DATE " +
						   "FROM member_info " +
						   "WHERE member_id = ? ";

			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, memberinfo.getMemberId());

			rs = pstmt.executeQuery();// row를 찾아옴
			rs.next(); 				 // 커서는 row를 지시
			created_date = rs.getDate(1);//date column의 value 가져옴

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
						+"SET highschool = ?, university = ?, location = ?, phone = ?, created_date = sysdate "
						+"WHERE member_id = ?";

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


	// member_one: loginuser 친구요청보냄(requestSender)
	public ArrayList<MemberInfo> memberInfosByMemberOneAndStatus(Member myMember, int status) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberInfos=new ArrayList<>();

		try {

			conn = ConnectionHelper.getConnection("oracle");

			// member_one, member_two: member_id의 FK(이름 달라도 FK될 수 있음: 서로 지칭하는 값이 같음)
			String sql = "SELECT location, highschool, phone, university, member_id, created_date "+
						 "FROM member_info " +
						 "WHERE member_id IN (SELECT member_two " +
											 "FROM friend " +
											 "WHERE member_one = ? AND status = ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);

			rs = pstmt.executeQuery();
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


	// member_two: member_id that got request 친구요청받음 (requestReceiver)
	public ArrayList<MemberInfo> memberInfosByMemberTwoAndStatus(Member myMember, int status) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberInfos = new ArrayList<>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			// member_one, member_two: member_id의 FK(이름 달라도 FK될 수 있음: 서로 지칭하는 값이 같음)
			String sql = "SELECT location, highschool, phone, university, member_id, created_date "
					   + "FROM member_info " +
					     "WHERE member_id IN (SELECT member_one " +
						 "					  FROM friend " +
						 "					  WHERE member_two = ? AND status = ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);

			rs = pstmt.executeQuery();
			while (rs.next()) {
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

			String sql = "SELECT member_id, name, passwd, email, gender, birthday "
					   + "FROM member " +
						 "WHERE member_id IN (SELECT member_one " +
						 "					  FROM friend " +
						 "					  WHERE member_two = ?  AND status = ? )";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);

			rs = pstmt.executeQuery();
			while (rs.next()) { // 단일 결과 검색이기때문에 if
				Member member = new Member();
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
		ArrayList<Member> members=new ArrayList<>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT member_id, name, passwd, email, gender, birthday "
					   + "FROM member " +
						 "WHERE member_id IN (SELECT member_two " +
						 "					  FROM friend " +
						 "					  WHERE member_one = ? AND status = ? )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myMember.getMemberId());
			pstmt.setInt(2, status);

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
