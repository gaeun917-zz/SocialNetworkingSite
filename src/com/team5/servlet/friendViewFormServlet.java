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

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.team5.dto.MemberInfo;
import com.google.gson.Gson;

@WebServlet("/friend/friendViewForm.action")
public class friendViewFormServlet extends HttpServlet {

	private final int STATUS=1;//친구인 관계를 추출해서 보여줘야하기 때문에 status가 1
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");
		
		MemberDao dao=new MemberDao();
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));
		
		ArrayList<MemberInfo> friendInfos=new ArrayList<MemberInfo>();
		ArrayList<Member> friends=new ArrayList<Member>();
		ArrayList<MemberInfo> friendInfos2=new ArrayList<MemberInfo>();
		ArrayList<Member> friends2=new ArrayList<Member>();
		
		//내가 요청을 받아서 친구가 된 사이
		friendInfos=dao.memberInfosByMemberTwoAndStatus(myMember, STATUS);
		friends=dao.membersByMemberTwoAndStatus(myMember, STATUS);
		
		//내가 요청을 보내서 친구가 된 사이
		friendInfos2=dao.memberInfosByMemberOneAndStatus(myMember, STATUS);
		friends2=dao.membersByMemberOneAndStatus(myMember, STATUS);
		
		request.setAttribute("friendInfos", friendInfos);
		request.setAttribute("friends", friends);
		request.setAttribute("friendInfos2", friendInfos2);
		request.setAttribute("friends2", friends2);
		
			RequestDispatcher dispatcher=
			request.getRequestDispatcher("/WEB-INF/views/include/friendViewForm.jsp");
			dispatcher.forward(request, response);
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















