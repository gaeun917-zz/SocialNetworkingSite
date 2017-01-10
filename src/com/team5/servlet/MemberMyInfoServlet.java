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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String memberId = req.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId);
		
		MemberDao dao = new MemberDao();
		MemberInfo memberinfo= dao.selectMemberInfoById(iMemberId);
		req.setAttribute("memberinfo", memberinfo);	
		 

		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/WEB-INF/views/member/Information.jsp");
			dispatcher.forward(req, resp);


		

	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);// 처리 내용이 같으므로 doGet으로 전달
		
	}

	

}
