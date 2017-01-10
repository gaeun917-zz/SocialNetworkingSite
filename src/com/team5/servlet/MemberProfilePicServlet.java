package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.BoardDao;
import com.team5.dao.UploadDao2;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.UploadFile;

@WebServlet("/member/updateprofilepic.action")
public class MemberProfilePicServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 데이터 읽기 (없으면 목록으로 이동)
				String memberid = request.getParameter("memberid");
				int imemberid = Integer.parseInt(memberid);
				String profilePic = request.getParameter("profilepic");
				
				System.out.println(memberid);
				Member member = new Member();
				member.setMemberId(imemberid);		
				member.setProfile_pic(profilePic);		
				
				//2. 데이터 insert
				UploadDao2 dao = new UploadDao2();
				dao.updateProfile_pic(member);
				
				response.setContentType("text/plain;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(profilePic);
				
				//out.println("error");
//				
//				
//				List<Board> boards = dao.selectBoardList(imemberid);
//				//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
//				request.setAttribute("boards", boards);	
//				
//				// 업로드된 모든 파일들의 목록을 가져온다.
//				List<UploadFile> uploadfiles = dao.selectUploadfileList(); 
//				request.setAttribute("uploadfiles", uploadfiles); 
//				
//				BoardDao dao2 = new BoardDao();
////				List<Board> boards = dao.selectBoardList();
//				List<Board> boards2 = dao2.selectBoardList();
//				
//
////				//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
//				request.setAttribute("boards2", boards2);
//				//3. detail로 이동
//				RequestDispatcher dispatcher = 
//						request.getRequestDispatcher(
//								"/WEB-INF/views/member/detail.jsp");
//					dispatcher.forward(request, response);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
}