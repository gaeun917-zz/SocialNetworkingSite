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

@WebServlet("/friend/friendViewForm.action")
public class friendViewFormServlet extends HttpServlet {

	private final int STATUS=1;//친구인 관계를 추출해서 보여줘야하기 때문에 status가 1

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			   			throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");

		//1. get & set data
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));

		MemberDao dao = new MemberDao();
		//내가 요청을 받아서 친구가 된 사이
		ArrayList<Member> friends = dao.membersByMemberTwoAndStatus(myMember, STATUS);
		request.setAttribute("friends", friends);
		ArrayList<MemberInfo> friendInfos = dao.memberInfosByMemberTwoAndStatus(myMember, STATUS);
		request.setAttribute("friendInfos", friendInfos);

		//내가 요청을 보내서 친구가 된 사이
		ArrayList<Member> friends2 = dao.membersByMemberOneAndStatus(myMember, STATUS);
		request.setAttribute("friends2", friends2);
		ArrayList<MemberInfo> friendInfos2 = dao.memberInfosByMemberOneAndStatus(myMember, STATUS);
		request.setAttribute("friendInfos2", friendInfos2);

		//3. redirect
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("/WEB-INF/views/include/friendViewForm.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















