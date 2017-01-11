package com.team5.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.team5.dto.MemberInfo;

@WebServlet("/friend/friendReceiveForm.action")
public class friendReceiveFormServelet extends HttpServlet {

	private final int STATUS=0;// 친구요청 받은 목록을 보여주기: status = 0

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");

		//1. get data & save it to Request
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));

		MemberDao dao = new MemberDao();
		ArrayList<MemberInfo> friendInfos = dao.memberInfosByMemberTwoAndStatus(myMember, STATUS);
		request.setAttribute("friendInfos", friendInfos);

		ArrayList<Member> friends = dao.membersByMemberTwoAndStatus(myMember, STATUS);
		request.setAttribute("friends", friends);

		//3. redirect
		RequestDispatcher dispatcher=
							request.getRequestDispatcher("/WEB-INF/views/include/friendReceiveForm.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {
		doGet(request, response);
	}
}