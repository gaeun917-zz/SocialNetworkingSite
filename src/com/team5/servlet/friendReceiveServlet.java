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

@WebServlet("/friend/friendReceive.action")
public class friendReceiveServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/plain;charset=utf-8");
		
		String requestSender=request.getParameter("memberId");
				
		MemberDao dao=new MemberDao();
		
		Member myMember = (Member)(request.getSession().getAttribute("loginuser"));
		
		dao.updateFriendAddFinally(requestSender, myMember);
		
		
			RequestDispatcher dispatcher=
			request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















