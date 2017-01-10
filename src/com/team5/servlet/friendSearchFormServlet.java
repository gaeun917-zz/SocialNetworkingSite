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
import com.google.gson.Gson;

@WebServlet("/friend/friendSearch.action")
public class friendSearchFormServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");
		
		String search=request.getParameter("search");
		
		MemberDao dao=new MemberDao();
		
		ArrayList<Member> searchByMember=dao.getMemberListByNameAndSchoolAndLocation(search);
		
		request.setAttribute("searchByMember", searchByMember);
		
		
			RequestDispatcher dispatcher=
			request.getRequestDispatcher("/WEB-INF/views/include/friendSearchForm.jsp");
			dispatcher.forward(request, response);
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















