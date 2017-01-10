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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher(
				"/WEB-INF/views/member/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. 데이터 읽기
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		String gender = req.getParameter("gender");
		String birth = req.getParameter("birth");
		
		
		
		
		//2. 데이터 처리 (DB에 데이터를 저장)
		Member member = new Member();
		member.setName(name);
		member.setEmail(email);
		member.setPasswd(passwd);
		member.setGender(gender);
		member.setBirth(birth);
		
		MemberDao dao = new MemberDao();
		//회원가입시 member테이블 insert와 memberInfo테이블 insert
		int memberId = dao.insertMember(member);
		member.setMemberId(memberId);
		
		
		
		req.setAttribute("member", member);
		System.out.println(member.getMemberId());
		
		//3. 결과에 따라 이동
		//지정된 경로로 요청을 보내는 명령
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher(
						"/WEB-INF/views/member/memberinput.jsp");
			dispatcher.forward(req, resp);
		//resp.sendRedirect("/team5/memberinput.jsp");
		
	}

	

}
