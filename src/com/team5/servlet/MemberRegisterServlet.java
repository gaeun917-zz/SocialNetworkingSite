package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.team5.dto.Member;

@WebServlet(value = "/register.action")
public class MemberRegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		//3. forward to jsp
		RequestDispatcher dispatcher =
			req.getRequestDispatcher("/WEB-INF/views/member/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. get data
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		String gender = req.getParameter("gender");
		String birth = req.getParameter("birth");

		//2. set data (save data in DB)
		Member member = new Member();
		MemberDao dao = new MemberDao();
		int memberId = dao.insertMember(member);
				member.setName(name);
				member.setEmail(email);
				member.setPasswd(passwd);
				member.setGender(gender);
				member.setBirth(birth);
				member.setMemberId(memberId);
		req.setAttribute("member", member);

		//3. forward(with req data) to jsp
		RequestDispatcher dispatcher =
				req.getRequestDispatcher("/WEB-INF/views/member/memberinput.jsp");
		dispatcher.forward(req, resp);
		//resp.sendRedirect("/team5/memberinput.jsp");
	}
}
