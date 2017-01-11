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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

		response.setContentType("text/plain;charset=utf-8");

		//1. get data
		String memberid = request.getParameter("memberid");
		System.out.println(memberid);
		int imemberid = Integer.parseInt(memberid);
		String profilePic = request.getParameter("profilepic");

		//2. set data (insert)
		Member member = new Member();
				member.setMemberId(imemberid);
				member.setProfile_pic(profilePic);
				
		UploadDao2 dao = new UploadDao2();
					dao.updateProfile_pic(member);
				
		PrintWriter out = response.getWriter();
					out.println(profilePic);
				

//		List<Board> boards = dao.selectBoardList(imemberid);
//				request.setAttribute("boards", boards);
//				
//		// list of all files uploaded
//		List<UploadFile> uploadfiles = dao.selectUploadfileList();
//		request.setAttribute("uploadfiles", uploadfiles);
//				
//		BoardDao dao2 = new BoardDao();
//		List<Board> boards2 = dao2.selectBoardList();
//		request.setAttribute("boards2", boards2);
//
// 		// 3. forward to jsp
//		RequestDispatcher dispatcher =
//				request.getRequestDispatcher("/WEB-INF/views/member/detail.jsp");
//		dispatcher.forward(request, response);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
}