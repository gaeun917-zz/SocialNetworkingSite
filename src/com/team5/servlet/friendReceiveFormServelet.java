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

	private final int STATUS=0;//친구가 아닌 관계에서 친구요청을 받은 목록을 보여줘야하기 때문에 status가 0
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");
		
		MemberDao dao=new MemberDao();
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));
		
		ArrayList<MemberInfo> friendInfos=dao.memberInfosByMemberTwoAndStatus(myMember, STATUS);
		ArrayList<Member> friends=dao.membersByMemberTwoAndStatus(myMember, STATUS);
		request.setAttribute("friendInfos", friendInfos);
		request.setAttribute("friends", friends);

		
			RequestDispatcher dispatcher=
			request.getRequestDispatcher("/WEB-INF/views/include/friendReceiveForm.jsp");
			dispatcher.forward(request, response);
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















