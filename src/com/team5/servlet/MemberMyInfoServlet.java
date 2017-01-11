package com.team5.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.MemberInfo;

@WebServlet(value = "/member/memberinfo.action")
public class MemberMyInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
							throws ServletException, IOException {

		//1. get data(memberId, memberInfo) & set data
		String memberId = req.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId);
		
		MemberDao dao = new MemberDao();
		MemberInfo memberinfo= dao.selectMemberInfoById(iMemberId);
		req.setAttribute("memberinfo", memberinfo);	
		 
		//3. forward to jsp
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/WEB-INF/views/member/Information.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);// same logic is used: method=post to method=get
	}
}
