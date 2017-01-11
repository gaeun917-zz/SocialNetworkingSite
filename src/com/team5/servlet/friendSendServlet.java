package com.team5.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;

@WebServlet("/friend/friendSend.action")
public class friendSendServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {

		response.setContentType("text/plain;charset=utf-8");

		//1. get data (memberId, loginuser)
		String addByFriendMemberId = request.getParameter("memberId");
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));

		//2. set data (update)
		MemberDao dao = new MemberDao();
		dao.updateFriendAdd(addByFriendMemberId, myMember);

		//3. redirect
		RequestDispatcher dispatcher=
						request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}















