package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.google.gson.Gson;

@WebServlet("/friend/friendSend.action")
public class friendSendServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/plain;charset=utf-8");
		
		String addByFriendMemberId=request.getParameter("memberId");
				
		MemberDao dao=new MemberDao();
		
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));
		
		//confirm이 true면 이전에 친구요청을 보낸 적이 있음
		//boolean confirm=dao.SelectFriendByMemberAndStatusZero(addByFriendMemberId, myMember);
		
		//confirm이 true면 이전에 친구요청을 보낸 적이 있음 false면 이전에 친구요청 한적이 없어서 친구요청 시도
		//if(confirm==false){
			dao.updateFriendAdd(addByFriendMemberId, myMember);
		//}
		
			RequestDispatcher dispatcher=
			request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















